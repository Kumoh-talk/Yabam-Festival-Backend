package domain.pos.table.service;

import static fixtures.member.UserFixture.*;
import static fixtures.store.StoreFixture.*;
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
import domain.pos.store.entity.Store;
import domain.pos.store.implement.StoreValidator;
import domain.pos.table.entity.Table;
import domain.pos.table.implement.TableReader;
import domain.pos.table.implement.TableWriter;

class TableServiceTest extends ServiceTest {

	@Mock
	private StoreValidator storeValidator;

	@Mock
	private TableReader tableReader;

	@Mock
	private TableWriter tableWriter;

	@InjectMocks
	private TableService tableService;

	@Nested
	@DisplayName("가게 테이블 생성")
	class createTable {
		@Test
		void 성공() {
			// given
			UserPassport queryUserPassport = OWNER_USER_PASSPORT();
			Long queryStoreId = GENERAL_STORE().getStoreId();
			Integer queryTableNumber = 1;

			Store responStore = GENERAL_STORE();
			Table createdTable = GENERAL_IN_ACTIVE_TABLE(responStore);

			doReturn(responStore)
				.when(storeValidator).validateStoreOwner(queryUserPassport, queryStoreId);
			doReturn(Optional.empty())
				.when(tableReader).existsTable(responStore, queryTableNumber);
			doReturn(createdTable)
				.when(tableWriter).createTable(responStore, queryTableNumber);
			// when
			Table resultTable = tableService.createTable(queryUserPassport, queryStoreId, queryTableNumber);

			// then
			assertSoftly(softly -> {
				softly.assertThat(resultTable.getTableId()).isEqualTo(createdTable.getTableId());
				softly.assertThat(resultTable.getTableNumber()).isEqualTo(createdTable.getTableNumber());
				softly.assertThat(resultTable.getStore().getStoreId()).isEqualTo(responStore.getStoreId());
				softly.assertThat(resultTable.getStore().getStoreId()).isEqualTo(responStore.getStoreId());

				verify(storeValidator).validateStoreOwner(queryUserPassport, queryStoreId);
				verify(tableReader)
					.existsTable(responStore, queryTableNumber);
				verify(tableWriter).createTable(responStore, queryTableNumber);
			});
		}

		@Test
		void 실패_유효하지_않은_storeId() {
			// given
			UserPassport queryUserPassport = OWNER_USER_PASSPORT();
			Long queryStoreId = 0L;
			Integer queryTableNumber = 1;

			doThrow(new ServiceException(ErrorCode.NOT_FOUND_STORE))
				.when(storeValidator).validateStoreOwner(queryUserPassport, queryStoreId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> tableService.createTable(queryUserPassport, queryStoreId, queryTableNumber))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);

				verify(storeValidator)
					.validateStoreOwner(queryUserPassport, queryStoreId);
				verify(tableReader, never())
					.existsTable(any(Store.class), anyInt());
				verify(tableWriter, never())
					.createTable(any(Store.class), anyInt());
			});
		}

		@Test
		void 실패_점주_주인과_요청유저가_불일치() {
			// given
			UserPassport queryUserPassport = OWNER_USER_PASSPORT();
			Long queryStoreId = GENERAL_STORE().getStoreId();
			Integer queryTableNumber = 1;

			doThrow(new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER))
				.when(storeValidator).validateStoreOwner(queryUserPassport, queryStoreId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> tableService.createTable(queryUserPassport, queryStoreId, queryTableNumber))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_EQUAL_STORE_OWNER);

				verify(storeValidator)
					.validateStoreOwner(queryUserPassport, queryStoreId);
				verify(tableReader, never())
					.existsTable(any(Store.class), anyInt());
				verify(tableWriter, never())
					.createTable(any(Store.class), anyInt());
			});
		}

		@Test
		void 실패_존재하는_테이블() {
			// given
			UserPassport queryUserPassport = OWNER_USER_PASSPORT();
			Long queryStoreId = GENERAL_STORE().getStoreId();
			Integer queryTableNumber = GENERAL_IN_ACTIVE_TABLE(GENERAL_STORE()).getTableNumber();

			Store responStore = GENERAL_STORE();
			Table createdTable = GENERAL_IN_ACTIVE_TABLE(responStore);

			doReturn(responStore)
				.when(storeValidator).validateStoreOwner(queryUserPassport, queryStoreId);
			doReturn(Optional.of(createdTable))
				.when(tableReader).existsTable(responStore, queryTableNumber);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> tableService.createTable(queryUserPassport, queryStoreId, queryTableNumber))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.EXIST_TABLE);

				verify(storeValidator)
					.validateStoreOwner(queryUserPassport, queryStoreId);
				verify(tableReader)
					.existsTable(responStore, queryTableNumber);
				verify(tableWriter, never())
					.createTable(responStore, queryTableNumber);
			});
		}
	}
}
