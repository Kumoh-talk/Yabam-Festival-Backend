package domain.pos.receipt.entity;

import domain.pos.member.entity.UserPassport;
import domain.pos.store.entity.Sale;
import domain.pos.table.entity.Table;
import lombok.Getter;

@Getter
public class Receipt {
	private final Long orderId;
	private final boolean isAdjustment; // 정산 여부
	private final UserPassport userPassport;
	private final Table table;
	private final Sale sale;

	private Receipt(Long orderId, boolean isAdjustment, UserPassport userPassport, Table table, Sale sale) {
		this.orderId = orderId;
		this.isAdjustment = isAdjustment;
		this.userPassport = userPassport;
		this.table = table;
		this.sale = sale;
	}

	public static Receipt of(
		final Long orderId,
		final boolean isAdjustment,
		final UserPassport userPassport,
		final Table table,
		final Sale sale) {
		return new Receipt(
			orderId,
			isAdjustment,
			userPassport,
			table,
			sale
		);
	}
}
