package domain.pos.menu;

import static fixtures.member.OwnerFixture.*;
import static fixtures.member.UserFixture.*;
import static fixtures.menu.MenuCategoryFixture.*;
import static fixtures.menu.MenuFixture.*;
import static fixtures.menu.MenuInfoFixture.*;
import static fixtures.store.StoreFixture.*;
import static fixtures.store.StoreInfoFixture.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import base.ServiceTest;
import domain.pos.member.entity.UserPassport;
import domain.pos.menu.entity.Menu;
import domain.pos.menu.entity.MenuCategory;
import domain.pos.menu.entity.MenuInfo;
import domain.pos.menu.implement.MenuCategoryValidator;
import domain.pos.menu.implement.MenuReader;
import domain.pos.menu.implement.MenuValidator;
import domain.pos.menu.implement.MenuWriter;
import domain.pos.menu.service.MenuService;
import domain.pos.store.entity.Store;
import domain.pos.store.entity.StoreInfo;
import domain.pos.store.implement.StoreReader;
import domain.pos.store.implement.StoreValidator;

public class MenuServiceTest extends ServiceTest {
	@Mock
	private StoreValidator storeValidator;
	@Mock
	private MenuValidator menuValidator;
	@Mock
	private MenuCategoryValidator menuCategoryValidator;
	@Mock
	private StoreReader storeReader;
	@Mock
	private MenuReader menuReader;
	@Mock
	private MenuWriter menuWriter;
	@InjectMocks
	private MenuService menuService;

	@Nested
	@DisplayName("메뉴 생성")
	class postMenu {
		private final Long storeId = 1L;
		private final UserPassport userPassport = OWNER_USER_PASSPORT();
		private final Long menuCategoryId = 3L;
		private final MenuInfo requestMenuInfo = REQUEST_MENU_INFO();

		private final Long menuId = 3L;

		@Test
		void 메뉴_생성_성공() {
			// given
			StoreInfo storeInfo = GENERAL_STORE_INFO();
			MenuCategory menuCategory = CUSTOM_MENU_CATEGORY(menuCategoryId, storeId);
			Menu menu = CUSTOM_MENU(REQUEST_TO_ENTITY(menuId, requestMenuInfo), storeInfo, menuCategory);

			BDDMockito.given(menuWriter.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo))
				.willReturn(menu);

			// when
			Menu serviceMenu = menuService.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo);

			// then
			assertSoftly(softly -> {
				MenuInfo serviceMenuInfo = serviceMenu.getMenuInfo();
				StoreInfo serviceStoreInfo = serviceMenu.getStoreInfo();
				MenuCategory serviceMenuCategory = serviceMenu.getMenuCategory();

				softly.assertThat(serviceMenuInfo.getMenuId()).isEqualTo(menuId);
				softly.assertThat(serviceMenuInfo.getMenuName()).isEqualTo(requestMenuInfo.getMenuName());
				softly.assertThat(serviceMenuInfo.getPrice()).isEqualTo(requestMenuInfo.getPrice());
				softly.assertThat(serviceMenuInfo.getDescription()).isEqualTo(requestMenuInfo.getDescription());
				softly.assertThat(serviceMenuInfo.getImageUrl()).isEqualTo(requestMenuInfo.getImageUrl());

				softly.assertThat(serviceStoreInfo.getStoreId()).isEqualTo(storeInfo.getStoreId());

				softly.assertThat(serviceMenuCategory.getMenuCategoryId()).isEqualTo(menuCategory.getMenuCategoryId());
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
						() -> menuService.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator, never())
					.validateMenuCategory(menuCategoryId);
				verify(menuWriter, never())
					.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo);
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
						() -> menuService.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_EQUAL_STORE_OWNER);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator, never())
					.validateMenuCategory(menuCategoryId);
				verify(menuWriter, never())
					.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo);
			});
		}

		@Test
		void 메뉴_카테고리_조회_실패() {
			// given
			doThrow(new ServiceException(ErrorCode.MENU_CATEGORY_NOT_FOUND))
				.when(menuCategoryValidator)
				.validateMenuCategory(menuCategoryId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuService.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.MENU_CATEGORY_NOT_FOUND);
				verify(storeValidator)
					.validateStoreOwner(userPassport, storeId);
				verify(menuCategoryValidator)
					.validateMenuCategory(menuCategoryId);
				verify(menuWriter, never())
					.postMenu(storeId, userPassport, menuCategoryId, requestMenuInfo);
			});
		}
	}

	@Nested
	@DisplayName("메뉴 리스트 조회")
	class getMenu {
		private final int size = 10;
		private final boolean hasNext = false;
		private final Pageable pageable = Pageable.ofSize(size);
		private final Long lastMenuId = 10L;
		private final Long storeId = 1L;
		private final Long menuCategoryId = 1L;

		private final Long userId = 1L;

		@Test
		void 메뉴_리스트_조회_성공() {
			// given
			Store store = CUSTOM_STORE(storeId, CUSTOM_STORE_INFO(storeId), CUSTOM_OWNER(userId));
			MenuInfo lastMenuInfo = CUSTOM_MENU_INFO(lastMenuId);
			MenuInfo nextMenuInfo = CUSTOM_MENU_INFO(lastMenuId + 1);
			Slice<MenuInfo> menuSlice = new SliceImpl<>(new ArrayList<>(List.of(nextMenuInfo)), pageable, hasNext);

			BDDMockito.given(storeReader.readSingleStore(storeId))
				.willReturn(Optional.of(store));
			BDDMockito.given(menuReader.getMenuInfo(lastMenuId))
				.willReturn(Optional.of(lastMenuInfo));
			BDDMockito.given(menuReader.getMenuSlice(pageable, lastMenuInfo, menuCategoryId))
				.willReturn(menuSlice);

			// when
			Slice<MenuInfo> serviceMenuSlice = menuService.getMenuSlice(pageable, lastMenuId, storeId, menuCategoryId);

			// then
			assertSoftly(softly -> {
				softly.assertThat(serviceMenuSlice.getSize()).isEqualTo(size);
				softly.assertThat(serviceMenuSlice.hasNext()).isEqualTo(hasNext);
				softly.assertThat(serviceMenuSlice.getContent().get(0).getMenuId()).isEqualTo(lastMenuId + 1);
			});
		}

		@Test
		void 주점_조회_실패() {
			// given
			BDDMockito.given(storeReader.readSingleStore(storeId))
				.willReturn(Optional.empty());

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuService.getMenuSlice(pageable, lastMenuId, storeId, menuCategoryId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_STORE);
				verify(storeReader)
					.readSingleStore(storeId);
				verify(menuCategoryValidator, never())
					.validateMenuCategory(menuCategoryId);
				verify(menuReader, never())
					.getMenuInfo(lastMenuId);
				verify(menuReader, never())
					.getMenuSlice(any(Pageable.class), any(MenuInfo.class), any(Long.class));
			});
		}

		@Test
		void 카테고리_조회_실패() {
			// given
			Store store = CUSTOM_STORE(storeId, CUSTOM_STORE_INFO(storeId), CUSTOM_OWNER(userId));
			BDDMockito.given(storeReader.readSingleStore(any(Long.class)))
				.willReturn(Optional.of(store));

			doThrow(new ServiceException(ErrorCode.MENU_CATEGORY_NOT_FOUND))
				.when(menuCategoryValidator)
				.validateMenuCategory(menuCategoryId);

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuService.getMenuSlice(pageable, lastMenuId, storeId, menuCategoryId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.MENU_CATEGORY_NOT_FOUND);
				verify(storeReader)
					.readSingleStore(storeId);
				verify(menuCategoryValidator)
					.validateMenuCategory(menuCategoryId);
				verify(menuReader, never())
					.getMenuInfo(lastMenuId);
				verify(menuReader, never())
					.getMenuSlice(any(Pageable.class), any(MenuInfo.class), any(Long.class));
			});
		}

		@Test
		void 메뉴_조회_실패() {
			// given
			Store store = CUSTOM_STORE(storeId, CUSTOM_STORE_INFO(storeId), CUSTOM_OWNER(userId));
			BDDMockito.given(storeReader.readSingleStore(any(Long.class)))
				.willReturn(Optional.of(store));
			BDDMockito.given(menuReader.getMenuInfo(any(Long.class)))
				.willReturn(Optional.empty());

			// when -> then
			assertSoftly(softly -> {
				softly.assertThatThrownBy(
						() -> menuService.getMenuSlice(pageable, lastMenuId, storeId, menuCategoryId))
					.isInstanceOf(ServiceException.class)
					.hasFieldOrPropertyWithValue("errorCode", ErrorCode.MENU_NOT_FOUND);
				verify(storeReader)
					.readSingleStore(storeId);
				verify(menuCategoryValidator)
					.validateMenuCategory(menuCategoryId);
				verify(menuReader)
					.getMenuInfo(lastMenuId);
				verify(menuReader, never())
					.getMenuSlice(any(Pageable.class), any(MenuInfo.class), any(Long.class));
			});
		}
	}

	@Nested
	@DisplayName("메뉴 수정")
	class patchMenu {
		private final String patchMenuName = "patchMenuName";
		private final int patchPrice = 20000;
		private final String patchDescription = "patchDescription";
		private final String patchImageUrl = "patchImageUrl";
		private final boolean patchIsSoldOut = false;

		private final Long storeId = 1L;
		private final UserPassport userPassport = OWNER_USER_PASSPORT();
		private final Long menuId = 3L;
		private final MenuInfo patchMenuInfo = CUSTOM_MENU_INFO(menuId, patchMenuName, patchPrice, patchDescription,
			patchImageUrl,
			patchIsSoldOut);

		private final Long menuCategoryId = 4L;
		private final int order = 1;

		@Test
		void 메뉴_수정_성공() {
			// given
			BDDMockito.given(menuWriter.patchMenu(patchMenuInfo))
				.willReturn(patchMenuInfo);

			// when
			MenuInfo servicePatchMenuInfo = menuService.patchMenu(storeId, userPassport, patchMenuInfo);

			// then
			assertSoftly(softly -> {
				softly.assertThat(servicePatchMenuInfo.getMenuName()).isEqualTo(patchMenuInfo.getMenuName());
				softly.assertThat(servicePatchMenuInfo.getPrice()).isEqualTo(patchMenuInfo.getPrice());
				softly.assertThat(servicePatchMenuInfo.getDescription()).isEqualTo(patchMenuInfo.getDescription());
				softly.assertThat(servicePatchMenuInfo.getImageUrl()).isEqualTo(patchMenuInfo.getImageUrl());
				softly.assertThat(servicePatchMenuInfo.isSoldOut()).isEqualTo(patchMenuInfo.isSoldOut());
			});
		}
	}

	@Nested
	@DisplayName("메뉴 삭제")
	class deleteMenu {
		private final Long storeId = 1L;
		private final UserPassport userPassport = OWNER_USER_PASSPORT();
		private final Long menuCategoryId = 3L;
		private final Long menuId = 4L;

		@Test
		void 메뉴_삭제_성공() {
			// when
			menuService.deleteMenu(storeId, userPassport, menuCategoryId, menuId);

			// then
			verify(menuWriter)
				.deleteMenu(storeId, menuCategoryId, menuId);
		}
	}
}
