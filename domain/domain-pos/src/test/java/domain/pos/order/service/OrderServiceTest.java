package domain.pos.order.service;

import static fixtures.order.OrderFixture.*;
import static fixtures.store.SaleFixture.*;
import static fixtures.table.TableFixture.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import base.ServiceTest;
import domain.pos.member.entity.UserPassport;
import domain.pos.order.entity.Order;
import domain.pos.order.implement.OrderWriter;
import domain.pos.store.entity.Sale;
import domain.pos.store.entity.Store;
import domain.pos.store.implement.SaleReader;
import domain.pos.table.entity.Table;
import domain.pos.table.implement.TableReader;
import domain.pos.table.implement.TableWriter;
import fixtures.member.UserFixture;
import fixtures.store.StoreFixture;

class OrderServiceTest extends ServiceTest {

	@Mock
	private TableReader tableReader;

	@Mock
	private SaleReader saleReader;

	@Mock
	private TableWriter tableWriter;

	@Mock
	private OrderWriter orderWriter;

	@InjectMocks
	private OrderService orderService;

	@Nested
	@DisplayName("주문 등록")
	class registerOrder {
		@Test
		void 성공() {
			// given
			Store savedStore = StoreFixture.GENERAL_STORE();
			Table savedInActiveTable = GENERAL_IN_ACTIVE_TABLE(savedStore);
			Sale savedSale = GENERAL_OPEN_SALE(savedStore);

			UserPassport queryUserPassport = UserFixture.GENERAL_USER_PASSPORT();
			Long queryTableId = savedInActiveTable.getTableId();
			Long querySaleId = savedSale.getSaleId();

			Table changedActiveTable = GENERAL_ACTIVE_TABLE(savedStore);
			Order createdOrder = ORDER_NON_ADJUSTMENT(
				queryUserPassport,
				changedActiveTable,
				savedSale
			);
			doReturn(Optional.of(savedInActiveTable))
				.when(tableReader).findLockTableById(queryTableId);
			doReturn(Optional.of(savedSale))
				.when(saleReader).readSingleSale(querySaleId);
			doReturn(changedActiveTable)
				.when(tableWriter).changeTableActiveStatus(true, savedInActiveTable);
			doReturn(createdOrder)
				.when(orderWriter).createOrder(queryUserPassport, changedActiveTable, savedSale);
			// when
			Order savedOrder = orderService.registerOrder(
				queryUserPassport,
				queryTableId,
				querySaleId
			);
			// then
			assertSoftly(softly -> {
				softly.assertThat(savedOrder.getTable()).isEqualTo(changedActiveTable);
				softly.assertThat(savedOrder.getSale()).isEqualTo(savedSale);
				softly.assertThat(savedOrder.isAdjustment()).isFalse();
				softly.assertThat(savedOrder.getTable().getIsActive()).isTrue();

				verify(saleReader).readSingleSale(anyLong());
				verify(tableReader).findLockTableById(anyLong());
				verify(tableWriter).changeTableActiveStatus(anyBoolean(), any(Table.class));
				verify(orderWriter).createOrder(any(UserPassport.class), any(Table.class), any(Sale.class));
			});

		}

		@Test
		void 실패_테이블이_활성화되어있는데_요청을_보낸_경우() {
			// given
			Store savedStore = StoreFixture.GENERAL_STORE();
			Table savedActiveTable = GENERAL_ACTIVE_TABLE(savedStore);
			Sale savedSale = GENERAL_OPEN_SALE(savedStore);

			UserPassport queryUserPassport = UserFixture.GENERAL_USER_PASSPORT();
			Long queryTableId = savedActiveTable.getTableId();
			Long querySaleId = savedSale.getSaleId();

			doReturn(Optional.of(savedActiveTable))
				.when(tableReader).findLockTableById(queryTableId);
			doReturn(Optional.of(savedSale))
				.when(saleReader).readSingleSale(querySaleId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> orderService.registerOrder(queryUserPassport, queryTableId, querySaleId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.ALREADY_ACTIVE_TABLE);

				verify(saleReader).readSingleSale(anyLong());
				verify(tableReader).findLockTableById(anyLong());
				verify(tableWriter, never())
					.changeTableActiveStatus(anyBoolean(), any(Table.class));
				verify(orderWriter, never())
					.createOrder(any(UserPassport.class), any(Table.class), any(Sale.class));
			});
		}

		@Test
		void 실패_유효하지_않은_sale_id() {
			// given
			Store savedStore = StoreFixture.GENERAL_STORE();
			Table savedActiveTable = GENERAL_ACTIVE_TABLE(savedStore);
			Sale savedSale = GENERAL_OPEN_SALE(savedStore);

			UserPassport queryUserPassport = UserFixture.GENERAL_USER_PASSPORT();
			Long queryTableId = savedActiveTable.getTableId();
			Long querySaleId = savedSale.getSaleId();

			doReturn(Optional.empty())
				.when(saleReader).readSingleSale(querySaleId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> orderService.registerOrder(queryUserPassport, queryTableId, querySaleId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_SALE);

				verify(saleReader).readSingleSale(anyLong());
				verify(tableReader, never())
					.findLockTableById(anyLong());
				verify(tableWriter, never())
					.changeTableActiveStatus(anyBoolean(), any(Table.class));
				verify(orderWriter, never())
					.createOrder(any(UserPassport.class), any(Table.class), any(Sale.class));
			});
		}

		@Test
		void 실패_유효하지_않은_table_id() {
			// given
			Store savedStore = StoreFixture.GENERAL_STORE();
			Table savedActiveTable = GENERAL_ACTIVE_TABLE(savedStore);
			Sale savedSale = GENERAL_OPEN_SALE(savedStore);

			UserPassport queryUserPassport = UserFixture.GENERAL_USER_PASSPORT();
			Long queryTableId = savedActiveTable.getTableId();
			Long querySaleId = savedSale.getSaleId();

			doReturn(Optional.of(savedSale))
				.when(saleReader).readSingleSale(querySaleId);
			doReturn(Optional.empty())
				.when(tableReader).findLockTableById(queryTableId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> orderService.registerOrder(queryUserPassport, queryTableId, querySaleId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_TABLE);

				verify(saleReader).readSingleSale(anyLong());
				verify(tableReader).findLockTableById(anyLong());
				verify(tableWriter, never())
					.changeTableActiveStatus(anyBoolean(), any(Table.class));
				verify(orderWriter, never())
					.createOrder(any(UserPassport.class), any(Table.class), any(Sale.class));
			});
		}
	}
}
