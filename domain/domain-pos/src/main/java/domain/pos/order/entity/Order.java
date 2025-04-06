package domain.pos.order.entity;

import domain.pos.member.entity.Customer;
import domain.pos.store.entity.Sale;
import domain.pos.table.entity.Table;
import lombok.Getter;

@Getter
public class Order {
	private final Long orderId;
	private final boolean isAdjustment; // 정산 여부
	private final Customer customer;
	private final Table table;
	private final Sale sale;

	private Order(Long orderId, boolean isAdjustment, Customer customer, Table table, Sale sale) {
		this.orderId = orderId;
		this.isAdjustment = isAdjustment;
		this.customer = customer;
		this.table = table;
		this.sale = sale;
	}

	public static Order of(
		final Long orderId,
		final boolean isAdjustment,
		final Customer customer,
		final Table table,
		final Sale sale) {
		return new Order(
			orderId,
			isAdjustment,
			customer,
			table,
			sale
		);
	}
}
