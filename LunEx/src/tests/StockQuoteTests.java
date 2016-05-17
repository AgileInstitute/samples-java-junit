package tests;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;





import lunEx.LunExServices;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;

import earthboundInvestments.StockQuote;

public class StockQuoteTests {
	@Test
	public void totalCalculatesCorrectly_UsingMockito() {
		// given
		LunExServices myService =
		    Mockito.mock(LunExServices.class);
		String symbol = "HE3";
		Mockito.when(myService.currentPrice(symbol)).thenReturn(12);

		StockQuote quote = new StockQuote(symbol, 100, myService);

		// when
		long total = quote.total();
		
		// then
		assertThat(total, is(1234L));
		quote.total();
		Mockito.verify(myService, Mockito.times(1)).currentPrice(symbol);
	}
	
	@Test
	public void totalCalculatesCorrectly_usingHandCraftedMock() {
		// given
		MockLunExServices myService = new MockLunExServices(12);
		
		StockQuote quote = new StockQuote("HE3", 100, myService);

		// when
		long total = quote.total();
		
		// then
		assertThat(total, is(1234L));
		quote.total();
		assertThat(myService.wasCalledOnceAndOnlyOnce(), is(true));
	}
	
	@Test
	@Ignore
	public void totalCalculatesCorrectly_feelThePain() {
		// given
		LunExServices myService = new LunExServices();
		
		StockQuote quote = new StockQuote("HE3", 100, myService);

		// when
		long total = quote.total();
		
		// then
		assertThat(total, is(11740L));
	}
}
