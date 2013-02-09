package org.opt4j.start;

import java.lang.reflect.Type;
import java.util.Set;

import org.opt4j.config.Task;
import org.opt4j.config.TaskStateListener;
import org.opt4j.core.Genotype;
import org.opt4j.core.Phenotype;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.ControlListener;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.operator.Parameters;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * The {@link Opt4JTask} executes one optimization process.
 * 
 * @author lukasiewycz
 * @see Task
 * 
 */
public class Opt4JTask extends Task implements ControlListener, OptimizerIterationListener {

	protected Optimizer optimizer = null;

	protected Injector injector = null;

	protected Injector parentInjector = null;

	protected final boolean closeOnStop;

	protected boolean isClosed = false;

	protected final boolean initializing = false;

	/**
	 * Constructs a {@link Opt4JTask}.
	 * 
	 */
	@Inject
	public Opt4JTask() {
		this(true);
	}

	/**
	 * Constructs a {@link Opt4JTask}.
	 * 
	 * @param closeOnStop
	 *            close automatically after optimization
	 */
	public Opt4JTask(boolean closeOnStop) {
		this.closeOnStop = closeOnStop;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.Task#execute()
	 */
	@Override
	public void execute() throws Exception {
		open();
		check(injector);

		Control control = injector.getInstance(Control.class);
		optimizer = injector.getInstance(Optimizer.class);
		stateChanged();
		control.addListener(this);
		optimizer.addOptimizerIterationListener(this);

		Optimizer optimizer = injector.getInstance(Optimizer.class);
		optimizer.startOptimization();

		if (closeOnStop) {
			close();
		}
	}

	/**
	 * Initialize with a parent {@link Injector}.
	 * 
	 * @param injector
	 *            the parent injector
	 */
	public void init(Injector injector) {
		this.parentInjector = injector;
	}

	/**
	 * Close the task.
	 */
	public synchronized void close() {
		optimizer = null;
		injector = null;
		isClosed = true;
	}

	/**
	 * Initialize a task manually before executing it. This enables to get instances of classes before the optimization
	 * starts.
	 */
	public synchronized void open() {
		if (injector == null && !isClosed) {
			if (!isInit) {
				throw new RuntimeException("Task is not initialized. Call method init(modules) first.");
			}
			if (parentInjector == null) {
				injector = Guice.createInjector(modules);
			} else {
				injector = parentInjector.createChildInjector(modules);
			}
		}
	}

	/**
	 * Returns the current iteration.
	 * 
	 * @return the current iteration
	 */
	public int getIteration() {
		Optimizer optimizer = getOptimizer();
		if (optimizer == null) {
			return 0;
		}
		return optimizer.getIteration();
	}

	/**
	 * Returns the instance of the given class.
	 * 
	 * @param <O>
	 *            the type of class
	 * @param type
	 *            the class
	 * @return the instance of the class
	 */
	public <O> O getInstance(Class<O> type) {
		Injector injector = getInjector();
		if (injector == null) {
			return null;
		}
		return injector.getInstance(type);
	}

	/**
	 * Returns the {@link Injector} of the task.
	 * 
	 * @return the injector
	 */
	protected Injector getInjector() {
		return injector;
	}

	/**
	 * Returns the {@link Optimizer} of the task.
	 * 
	 * @return the optimizer
	 */
	protected Optimizer getOptimizer() {
		return optimizer;
	}

	/**
	 * Checks for configuration errors in the {@link Injector}.
	 * 
	 * @param injector
	 *            the injector
	 */
	private void check(Injector injector) {

		Creator<?> creator = null;
		Decoder<?, ?> decoder = null;
		Set<Evaluator> evaluators = null;

		try {
			creator = injector.getInstance(Creator.class);
			decoder = injector.getInstance(Decoder.class);
			evaluators = injector.getInstance(Key.get(new TypeLiteral<Set<Evaluator>>() {
			}));
		} catch (Exception e) {
			throw new RuntimeException("Problem configuration Exception: \n" + e.getLocalizedMessage(), e);
		}
		try {
			injector.getInstance(Optimizer.class);
		} catch (Exception e) {
			throw new RuntimeException("Optimizer configuration Exception: \n" + e.getLocalizedMessage(), e);
		}

		try {

			Type creatorType0 = Parameters.getType(Creator.class, creator, "G");
			Type decoderType0 = Parameters.getType(Decoder.class, decoder, "G");
			Type decoderType1 = Parameters.getType(Decoder.class, decoder, "P");
			Type evaluatorType0 = Parameters.getType(Evaluator.class, evaluators, "P");

			Class<?> creatorGenotype = Parameters.getClass(creatorType0);
			Class<?> decoderGenotype = Parameters.getClass(decoderType0);
			Class<?> decoderPhenotype = Parameters.getClass(decoderType1);
			Class<?> evaluatorPhenotype = Parameters.getClass(evaluatorType0);

			if (creatorGenotype == null) {
				creatorGenotype = Genotype.class;
			}
			if (decoderGenotype == null) {
				decoderGenotype = Genotype.class;
			}
			if (decoderPhenotype == null) {
				decoderPhenotype = Phenotype.class;
			}
			if (evaluatorPhenotype == null) {
				evaluatorPhenotype = Phenotype.class;
			}

			boolean a = decoderGenotype.isAssignableFrom(creatorGenotype);
			boolean b = evaluatorPhenotype.isAssignableFrom(decoderPhenotype);

			if (!a) {
				throw new IllegalArgumentException("Creator(" + creator.getClass() + ") and Decoder("
						+ decoder.getClass() + ") are not compatible. Creator creates \"" + creatorGenotype
						+ "\", Decoder decodes \"" + decoderGenotype + "\".");
			}
			if (!b) {
				throw new IllegalArgumentException("Decoder(" + decoder.getClass() + ") and Evaluator(" + evaluators
						+ ") are not compatible. Decoder decodes \"" + decoderPhenotype + "\", Evaluator evaluates \""
						+ evaluatorPhenotype + "\".");
			}
		} catch (NullPointerException e) {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.ControlListener#stateChanged(org.opt4j.core. optimizer.Control.State)
	 */
	@Override
	public void stateChanged(org.opt4j.core.optimizer.Control.State state) {
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete (org.opt4j.core.optimizer.Optimizer,
	 * int)
	 */
	@Override
	public void iterationComplete(int iteration) {
		stateChanged();
	}

	/**
	 * Calls the listeners that listen to the state of this task.
	 */
	private void stateChanged() {
		for (TaskStateListener listener : listeners) {
			listener.stateChanged(this);
		}
	}

}
