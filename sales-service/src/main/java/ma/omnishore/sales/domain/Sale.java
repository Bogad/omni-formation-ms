package ma.omnishore.sales.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "product_code")
	private String productCode;
	@Column(name = "client_id")
	private Long clientId;
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "operation_date")
	private Date operationDate;
	@Column(name = "quantity")
	private Long quantity;
	@Column(name = "amount")
	private Double amount;

	public Sale(String productCode, Long clientId, Date operationDate, Long quantity, Double amount) {
		super();
		this.productCode = productCode;
		this.clientId = clientId;
		this.operationDate = operationDate;
		this.quantity = quantity;
		this.amount = amount;
	}

	public Sale() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
