package fixtures.receipt;

import domain.pos.member.entity.UserPassport;
import domain.pos.receipt.entity.Receipt;
import domain.pos.store.entity.Sale;
import domain.pos.table.entity.Table;

public class ReceiptFixture {
	// order 고유 ID
	public static final Long GENERAL_ORDER_ID = 1L;

	// order 정산 여부
	private static final boolean ORDER_IS_ADJUSTMENT = true;
	private static final boolean ORDER_IS_NOT_ADJUSTMENT = false;

	public static Receipt RECEIPT_NON_ADJUSTMENT(final UserPassport userPassport, final Table table, final Sale sale) {
		return Receipt.of(
			GENERAL_ORDER_ID,
			ORDER_IS_NOT_ADJUSTMENT,
			userPassport,
			table,
			sale
		);
	}

	public static Receipt RECEIPT_ADJUSTMENT(final UserPassport userPassport, final Table table, final Sale sale) {
		return Receipt.of(
			GENERAL_ORDER_ID,
			ORDER_IS_ADJUSTMENT,
			userPassport,
			table,
			sale
		);
	}

}
