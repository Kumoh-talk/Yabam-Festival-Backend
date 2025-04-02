package domain.pos.menu.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import domain.pos.menu.entity.Menu;
import domain.pos.menu.entity.MenuInfo;

public interface MenuRepository {
	Menu postMenu(Long storeId, Long userId, Long menuCategoryId, MenuInfo menuInfo);

	Optional<MenuInfo> getMenuInfo(Long menuId);

	Slice<MenuInfo> getMenuSlice(Pageable pageable, MenuInfo lastMenuInfo, Long menuCategoryId);
}
