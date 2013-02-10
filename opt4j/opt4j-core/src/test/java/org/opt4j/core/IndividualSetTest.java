package org.opt4j.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.opt4j.core.optimizer.Population;


public class IndividualSetTest {
	
	protected final static int SIZE = 10000;
	
	List<Individual> list = new ArrayList<Individual>();
	
	@Before
	public void setUp() throws Exception {
		for(int i=0; i<SIZE; i++){
			Individual individual = new Individual();
			list.add(individual);
		}
	}
	
	@Test
	public void testPopulationOrder(){
		Population p0 = new Population();
		Population p1 = new Population();
		p0.addAll(list);
		p1.addAll(list);
		
		Iterator<Individual> it0 = p0.iterator();
		Iterator<Individual> it1 = p1.iterator();
		
		while(it0.hasNext()){
			Individual i0 = it0.next();
			Individual i1 = it1.next();
			Assert.assertEquals(i0, i1);
		}
	}

}
