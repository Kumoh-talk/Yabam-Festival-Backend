package domain.pos.menu;

import static fixtures.member.UserFixture.*;
import static fixtures.menu.MenuCategoryFixture.*;
import static fixtures.store.StoreFixture.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import base.ServiceTest;
import domain.pos.member.entity.UserPassport;
import domain.pos.menu.entity.MenuCategory;
import domain.pos.menu.implement.MenuCategoryReader;
import domain.pos.menu.implement.MenuCategoryValidator;
import domain.pos.menu.implement.MenuCategoryWriter;
import domain.pos.menu.service.MenuCategoryService;
import domain.pos.store.implement.StoreReader;
import domain.pos.store.implement.StoreValidator;

public class MenuCategoryServiceTest extends ServiceTest {
	@Mock
	private StoreValidator storeValidator;
	@Mock
	private MenuCategoryValidator menuCategoryValidator;
	@Mock
	private StoreReader storeReader;
	@Mock
	private MenuCategoryWriter menuCategoryWriter;
	@Mock
	private MenuCategoryReader menuCategoryReader;
	@InjectMocks
	private MenuCategoryService menuCategoryService;

	@Nested
	@DisplayName("메뉴 카테고리 생성")
	class postMenuCategory {
		private final Long storeId = 1L;
		private final UserPassport userPassport = OWNER_USER_PASSPORT();
		private final String categoryName = "categoryName";

		private final Long menuCategoryId = 1L;

		@Test
		void 메뉴_카테고리_생성_성공() {
			// given
			MenuCategory menuCategory = CUSTOM_MENU_CATEGORY(menuCategoryId, categoryName, storeId);

			BDDMockito.given(menuCategoryWriter.postMenuCategory(storeId, categoryName))
				.willReturn(menuCategory);

			// when
			MenuCategory serviceMenuCategory = menuCategoryService.postMenuCategory(storeId, userPassport,
				categoryName);

			// then
			assertSoftly(softly -> {
				softly.assertThat(serviceMenuCategory.getMenuCategoryId()).isEqualTo(menuCategoryId);
				softly.assertThat(serviceMenuCategory.getMenuCategoryName()).isEqualTo(categoryName);
			});
		}

		@Test
		void 주점_조회_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_FOUND_STORE))
				.when(storeValidator)
				.validateStoreOwner(userPassport, storeId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuCategoryService.postMenuCategory(storeId, userPassport, categoryName))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryWriter, never())
					.postMenuCategory(storeId, categoryName);
			});
		}

		@Test
		void 점주_인증_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER))
				.when(storeValidator)
				.validateStoreOwner(userPassport, storeId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuCategoryService.postMenuCategory(storeId, userPassport, categoryName))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_EQUAL_STORE_OWNER);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryWriter, never())
					.postMenuCategory(storeId, categoryName);
			});
		}
	}

	@Nested
	@DisplayName("메뉴 카테고리 리스트 조회")
	class getMenuCategoryList {
		private final Long menuCategoryId = MENU_CATEGORY_ID;
		private final String categoryName = CATEGORY_NAME;
		private final Long storeId = STORE_ID;

		@Test
		void 메뉴_카테고리_리스트_조회_성공() {
			// given
			MenuCategory menuCategory = GENERAL_MENU_CATEGORY();

			BDDMockito.given(storeReader.readSingleStore(storeId))
				.willReturn(Optional.of(CUSTOM_STORE(storeId, null, null)));
			BDDMockito.given(menuCategoryReader.getMenuCategoryList(storeId))
				.willReturn(List.of(menuCategory));

			// when
			List<MenuCategory> menuCategoryList = menuCategoryService.getMenuCategoryList(storeId);

			// then
			assertSoftly(softly -> {
				softly.assertThat(menuCategoryList.get(0).getMenuCategoryId()).isEqualTo(menuCategoryId);
				softly.assertThat(menuCategoryList.get(0).getMenuCategoryName()).isEqualTo(categoryName);
				softly.assertThat(menuCategoryList.get(0).getStoreId()).isEqualTo(storeId);
			});
		}

		@Test
		void 가게_조회_실패() {
			// given
			BDDMockito.given(storeReader.readSingleStore(storeId))
				.willReturn(Optional.empty());

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(() -> menuCategoryService.getMenuCategoryList(storeId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);
				verify(storeReader)
					.readSingleStore(storeId);
				verify(menuCategoryReader, never())
					.getMenuCategoryList(storeId);
			});
		}
	}

	@Nested
	@DisplayName("메뉴 카테고리 수정")
	class patchMenuCategory {
		private final Long storeId = 1L;
		private final UserPassport userPassport = OWNER_USER_PASSPORT();
		private final Long categoryId = 3L;
		private final String categoryName = "categoryName";

		@Test
		void 메뉴_카테고리_수정_성공() {
			// given
			MenuCategory menuCategory = CUSTOM_MENU_CATEGORY(categoryId, categoryName, storeId);

			BDDMockito.given(menuCategoryWriter.patchMenuCategory(categoryId, categoryName))
				.willReturn(menuCategory);

			// when
			MenuCategory serviceMenuCategory = menuCategoryService.patchMenuCategory(storeId, userPassport, categoryId,
				categoryName);

			// then
			assertSoftly(softly -> {
				softly.assertThat(serviceMenuCategory.getMenuCategoryId()).isEqualTo(categoryId);
				softly.assertThat(serviceMenuCategory.getMenuCategoryName()).isEqualTo(categoryName);
				softly.assertThat(serviceMenuCategory.getStoreId()).isEqualTo(storeId);
			});
		}

		@Test
		void 가게_조회_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_FOUND_STORE))
				.when(storeValidator)
				.validateStoreOwner(userPassport, storeId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(() -> menuCategoryService.patchMenuCategory(storeId, userPassport, categoryId,
						categoryName))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator, never())
					.validateMenuCategory(categoryId);
				verify(menuCategoryWriter, never())
					.patchMenuCategory(categoryId, categoryName);
			});
		}

		@Test
		void 점주_인증_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER))
				.when(storeValidator)
				.validateStoreOwner(userPassport, storeId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(() -> menuCategoryService.patchMenuCategory(storeId, userPassport, categoryId,
						categoryName))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_EQUAL_STORE_OWNER);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator, never())
					.validateMenuCategory(categoryId);
				verify(menuCategoryWriter, never())
					.patchMenuCategory(categoryId, categoryName);
			});
		}

		@Test
		void 메뉴_카테고리_조회_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.MENU_CATEGORY_NOT_FOUND))
				.when(menuCategoryValidator)
				.validateMenuCategory(categoryId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(() -> menuCategoryService.patchMenuCategory(storeId, userPassport, categoryId,
						categoryName))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.MENU_CATEGORY_NOT_FOUND);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator)
					.validateMenuCategory(categoryId);
				verify(menuCategoryWriter, never())
					.patchMenuCategory(categoryId, categoryName);
			});
		}
	}

	@Nested
	@DisplayName("메뉴 카테고리 삭제")
	class deleteMenuCategory {
		private final Long storeId = 1L;
		private final UserPassport userPassport = OWNER_USER_PASSPORT();
		private final Long categoryId = 3L;

		@Test
		void 메뉴_카테고리_삭제_성공() {
			// when
			menuCategoryService.deleteMenuCategory(storeId, userPassport, categoryId);

			// then
			verify(menuCategoryWriter)
				.deleteMenuCategory(categoryId);

		}

		@Test
		void 가게_조회_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_FOUND_STORE))
				.when(storeValidator)
				.validateStoreOwner(userPassport, storeId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuCategoryService.deleteMenuCategory(storeId, userPassport, categoryId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator, never())
					.validateMenuCategory(categoryId);
				verify(menuCategoryWriter, never())
					.deleteMenuCategory(categoryId);
			});
		}

		@Test
		void 점주_인증_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.NOT_EQUAL_STORE_OWNER))
				.when(storeValidator)
				.validateStoreOwner(userPassport, storeId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuCategoryService.deleteMenuCategory(storeId, userPassport, categoryId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_EQUAL_STORE_OWNER);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator, never())
					.validateMenuCategory(categoryId);
				verify(menuCategoryWriter, never())
					.deleteMenuCategory(categoryId);
			});
		}

		@Test
		void 메뉴_카테고리_조회_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.MENU_CATEGORY_NOT_FOUND))
				.when(menuCategoryValidator)
				.validateMenuCategory(categoryId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuCategoryService.deleteMenuCategory(storeId, userPassport, categoryId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.MENU_CATEGORY_NOT_FOUND);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator)
					.validateMenuCategory(categoryId);
				verify(menuCategoryWriter, never())
					.deleteMenuCategory(categoryId);
			});
		}
	}
}
