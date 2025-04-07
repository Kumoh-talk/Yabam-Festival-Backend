package domain.pos.receipt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exception.ErrorCode;
import com.exception.ServiceException;

import domain.pos.member.entity.UserPassport;
import domain.pos.receipt.entity.Receipt;
import domain.pos.receipt.implement.ReceiptWriter;
import domain.pos.store.entity.Sale;
import domain.pos.store.implement.SaleReader;
import domain.pos.table.entity.Table;
import domain.pos.table.implement.TableReader;
import domain.pos.table.implement.TableWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptService {
	private final TableReader tableReader;
	private final SaleReader saleReader;
	private final TableWriter tableWriter;
	private final ReceiptWriter receiptWriter;

	@Transactional
	public Receipt registerReceipt(final UserPassport queryUserPassport, final Long queryTableId,
		final Long querySaleId) {
		final Sale savedSale = saleReader.readSingleSale(querySaleId)
			.orElseThrow(() -> {
				log.warn("Sale 을 찾을 수 없습니다. saleId: {}, userId: {}", querySaleId, queryUserPassport.getUserId());
				throw new ServiceException(ErrorCode.NOT_FOUND_SALE);
			});

		final Table savedTable = tableReader.findLockTableById(queryTableId)
			.orElseThrow(() -> {
				log.warn("Table 을 찾을 수 없습니다. tableId: {}, userId: {}", queryTableId, queryUserPassport.getUserId());
				throw new ServiceException(ErrorCode.NOT_FOUND_TABLE);
			});

		if (savedTable.getIsActive()) {
			log.warn("Table 이 이미 활성화 되어 있습니다. tableId: {}, userId: {}", queryTableId, queryUserPassport.getUserId());
			throw new ServiceException(ErrorCode.ALREADY_ACTIVE_TABLE);
		}

		final Table changedActiveTable = tableWriter.changeTableActiveStatus(true, savedTable);
		final Receipt createdReceipt = receiptWriter.createReceipt(queryUserPassport, changedActiveTable, savedSale);
		return createdReceipt;
	}
}
