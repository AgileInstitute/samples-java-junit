package earthboundInvestments;

import lunEx.LunExServices;

public class StockQuote {
	private final long total;
	public StockQuote(String symbol, int numberOfShares, LunExServices service) {
		total = (long) (service.currentPrice(symbol) * numberOfShares * 1.02) + 10;
	}

	public long total() {
		return total;
	}
}
