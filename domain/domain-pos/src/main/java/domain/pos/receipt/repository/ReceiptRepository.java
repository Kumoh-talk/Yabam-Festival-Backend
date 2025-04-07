package domain.pos.receipt.repository;

import domain.pos.member.entity.UserPassport;
import domain.pos.receipt.entity.Receipt;
import domain.pos.store.entity.Sale;
import domain.pos.table.entity.Table;

public interface ReceiptRepository {

	Receipt createReceipt(UserPassport userPassport, Table table, Sale sale);
}
