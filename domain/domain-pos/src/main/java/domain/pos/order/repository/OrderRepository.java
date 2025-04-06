package domain.pos.order.repository;

import domain.pos.member.entity.UserPassport;
import domain.pos.order.entity.Order;
import domain.pos.store.entity.Sale;
import domain.pos.table.entity.Table;

public interface OrderRepository {

	Order createOrder(UserPassport userPassport, Table table, Sale sale);
}
