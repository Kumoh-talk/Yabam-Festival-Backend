package domain.pos.menu.implement;

import org.springframework.stereotype.Component;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MenuValidator {
	private final MenuReader menuReader;

	public void validateMenu(Long menuId) {
		menuReader.getMenuInfo(menuId)
			.orElseThrow(() -> new ServiceException(ErrorCode.MENU_NOT_FOUND));
	}
}
