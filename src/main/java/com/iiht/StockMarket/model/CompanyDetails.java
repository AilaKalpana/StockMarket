package com.iiht.StockMarket.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CompanyDetails")
public class CompanyDetails implements Serializable {

	private static final long serialVersionUID = 531050392622113165L;

	@Id
	@Column(name = "companyCode")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "companyCode cannot be omitted")
	private Long companyCode;
	private String stockExchange;

	@NotNull(message = "companyName cannot be omitted")
	@NotBlank(message = "companyName cannot be omitted")
	@Size(min = 3, max = 100, message = "companyName must be of 3 to 100 chars in length")
	private String companyName;

	@NotNull(message = "companyCEO cannot be omitted")
	@NotBlank(message = "companyCEO cannot be omitted")
	@Size(min = 5, max = 100, message = "companyCEO must be of 5 to 100 chars in length")
	private String companyCEO;

	@Column(nullable = false, precision = 10, scale = 2)
	private Double turnover;

	@NotNull(message = "boardOfDirectors cannot be omitted")
	@NotBlank(message = "boardOfDirectors cannot be omitted")
	@Size(min = 5, max = 200, message = "boardOfDirectors must be of 5 to 200 chars in length")
	private String boardOfDirectors;

	@NotNull(message = "companyProfile cannot be omitted")
	@NotBlank(message = "companyProfile cannot be omitted")
	@Size(min = 5, max = 200, message = "companyProfile must be of 5 to 255 chars in length")
	private String companyProfile;

	// ---------------------------------------------------------------------------------------------------------------------------------
	public CompanyDetails() {
		super();
	}

	public CompanyDetails(Long companyCode, String stockExchange, String companyName, String companyCEO,
			Double turnover, String boardOfDirectors, String companyProfile) {
		super();
		this.companyCode = companyCode;
		this.stockExchange = stockExchange;
		this.companyName = companyName;
		this.companyCEO = companyCEO;
		this.turnover = turnover;
		this.boardOfDirectors = boardOfDirectors;
		this.companyProfile = companyProfile;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Long getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(Long companyCode) {
		this.companyCode = companyCode;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyCEO() {
		return companyCEO;
	}

	public void setCompanyCEO(String companyCEO) {
		this.companyCEO = companyCEO;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Double getTurnover() {
		return turnover;
	}

	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getBoardOfDirectors() {
		return boardOfDirectors;
	}

	public void setBoardOfDirectors(String boardOfDirectors) {
		this.boardOfDirectors = boardOfDirectors;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public String getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	public Set<StockPriceDetails> getStockPriceDetails() {
		return StockPriceDetails;
	}

	public void setStockPriceDetails(Set<StockPriceDetails> stockPriceDetails) {
		StockPriceDetails = stockPriceDetails;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------
	@OneToMany(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "companyCode")
	private Set<StockPriceDetails> StockPriceDetails;
}