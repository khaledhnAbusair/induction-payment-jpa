package repositories.sqlconstants;

public class SqlConstants {
	public static final String UPDATE_ACCOUNT_QUERY = "update Account as a set a.type=:type,a.balance=:balance,a.status=:status,a.currency=:currencyCode,a.rule=:rule where a.iban =:iban ";
	public static final String SELECT_PAYMENT_REQUEST_FOR_PAYMENT_DATE = "select  pr from PaymentRequest as pr where pr.paymentDate=:paymentDate";
	public static final String SELECT_PAYMENT_REQUEST_FOR_ORDARING_IBAN = "select  pr from PaymentRequest as pr where pr.ordIban=:ordIban";
	public static final String 	UPDATE_PAYMENT_PURPOSE = "update PaymentPurpose as p set p.name=:name where p.code=:code";
	public static final String FIND_CURRENCY_BY_IBAN = "select curr from Currency curr where curr.code = :currencyCode";
	public static final String FIND_ALL_ACCOUNTS_QUERY = "Account.findAll";

	private SqlConstants() {

	}
}
