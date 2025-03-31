package domain.pos.menu.implement;

import org.springframework.stereotype.Component;

import domain.pos.menu.entity.Menu;
import domain.pos.menu.entity.MenuInfo;
import domain.pos.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuWriter {
	private final MenuRepository menuRepository;

	public Menu postMenu(Long storeId, Long userId, Long menuCategoryId, MenuInfo menuInfo) {
		return menuRepository.postMenu(storeId, userId, menuCategoryId, menuInfo);
	}
}
