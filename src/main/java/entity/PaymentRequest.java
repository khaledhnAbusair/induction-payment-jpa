package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "paymentRequest")
@NamedQuery(name = "PaymentRequest.findAll", query = "SELECT p FROM PaymentRequest p")
public class PaymentRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private int id;

	@Column(precision = 10, scale = 3)
	private BigDecimal amount;

	@Column(length = 255)
	private String amountInWords;

	@Column(length = 40)
	private String benefIban;

	@Column(nullable = false, length = 32)
	private String benefName;

	@Column(length = 3)
	private String currencyCode;

	@Column(length = 40)
	private String ordIban;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date paymentDate;

	@ManyToOne
	@JoinColumn(name = "purposeCode")
	private PaymentPurpose paymentPurpose;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAmountInWords() {
		return this.amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getBenefIban() {
		return this.benefIban;
	}

	public void setBenefIban(String benefIban) {
		this.benefIban = benefIban;
	}

	public String getBenefName() {
		return this.benefName;
	}

	public void setBenefName(String benefName) {
		this.benefName = benefName;
	}

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrdIban() {
		return this.ordIban;
	}

	public void setOrdIban(String ordIban) {
		this.ordIban = ordIban;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentPurpose getPaymentPurpose() {
		return this.paymentPurpose;
	}

	public void setPaymentPurpose(PaymentPurpose paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

}