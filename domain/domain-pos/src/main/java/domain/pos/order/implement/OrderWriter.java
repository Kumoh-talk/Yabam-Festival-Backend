package domain.pos.order.implement;

import org.springframework.stereotype.Component;

import domain.pos.member.entity.UserPassport;
import domain.pos.order.entity.Order;
import domain.pos.order.repository.OrderRepository;
import domain.pos.store.entity.Sale;
import domain.pos.table.entity.Table;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderWriter {
	private final OrderRepository orderRepository;

	public Order createOrder(UserPassport userPassport, Table table, Sale sale) {
		return orderRepository.createOrder(userPassport, table, sale);
	}
}
