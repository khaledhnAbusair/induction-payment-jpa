package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "paymentPurpose")
@NamedQuery(name = "PaymentPurpose.findAll", query = "SELECT p FROM PaymentPurpose p")
public class PaymentPurpose implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false, length = 4)
	private String code;

	@Column(nullable = false, length = 32)
	private String name;

	@OneToMany(mappedBy = "paymentPurpose")
	private List<PaymentRequest> paymentRequests;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PaymentRequest> getPaymentRequests() {
		return this.paymentRequests;
	}

	public void setPaymentRequests(List<PaymentRequest> paymentRequests) {
		this.paymentRequests = paymentRequests;
	}

	public PaymentRequest addPaymentRequest(PaymentRequest paymentRequest) {
		getPaymentRequests().add(paymentRequest);
		paymentRequest.setPaymentPurpose(this);

		return paymentRequest;
	}

	public PaymentRequest removePaymentRequest(PaymentRequest paymentRequest) {
		getPaymentRequests().remove(paymentRequest);
		paymentRequest.setPaymentPurpose(null);

		return paymentRequest;
	}

}