package com.iiht.StockMarket.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.StockMarket.dto.StockPriceDetailsDTO;
import com.iiht.StockMarket.dto.StockPriceIndexDTO;
import com.iiht.StockMarket.exception.InvalidStockException;
import com.iiht.StockMarket.model.CompanyDetails;
import com.iiht.StockMarket.model.StockPriceDetails;
import com.iiht.StockMarket.utils.StockMarketUtility;

import com.iiht.StockMarket.repository.StockPriceRepository;
import com.iiht.StockMarket.repository.CompanyInfoRepository;

@Service
@Transactional
public class StockMarketServiceImpl implements StockMarketService {

	@Autowired
	private StockPriceRepository stockRepository;

	@Autowired
	private CompanyInfoRepository repository;

	// ----------------------------------------------------------------------------
	public StockPriceDetailsDTO saveStockPriceDetails(StockPriceDetailsDTO stockPriceDetailsDTO) {

		StockPriceDetails stockPriceDetails = StockMarketUtility.convertToStockPriceDetails(stockPriceDetailsDTO);
		stockPriceDetails.setStockPriceDate(LocalDate.now());
		stockPriceDetails.setStockPriceTime(LocalTime.now());
		StockPriceDetails stockPriceDetails1 = stockRepository.save(stockPriceDetails);
		return StockMarketUtility.convertToStockPriceDetailsDTO(stockPriceDetails1);
	}

	// ----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> deleteStock(Long companyCode) {

		List<StockPriceDetails> stockPriceDetails = stockRepository.findStockByCompanyCode(companyCode);
		if (stockPriceDetails != null) {
			stockRepository.deleteAll(stockPriceDetails);
		} else {
			throw new InvalidStockException("Company Code does not exists..!");
		}
		List<StockPriceDetails> stockPriceDetailsList = stockRepository.findAll();
		List<StockPriceDetailsDTO> stockPriceDetailsDTOList = StockMarketUtility
				.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
		return stockPriceDetailsDTOList;
	}

	// ----------------------------------------------------------------------------
	public List<StockPriceDetailsDTO> getStockByCode(Long companyCode) {

		List<StockPriceDetails> stockPriceDetailsList = stockRepository.findStockByCompanyCode(companyCode);
		if (stockPriceDetailsList == null) {
			throw new InvalidStockException("Company Code does not exists");
		}
		return StockMarketUtility.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
	};

	public List<StockPriceDetailsDTO> getAllStock() {
		return StockMarketUtility.convertToStockPriceDetailsDtoList(stockRepository.findAll());
	}

	// ----------------------------------------------------------------------------
	public StockPriceDetailsDTO getStockPriceDetailsDTO(StockPriceDetails stockDetails) {
		return null;
	}

	// ----------------------------------------------------------------------------
	public Double getMaxStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		List<StockPriceDetails> stockPriceDetailsList = stockRepository.findStockByCompanyCode(companyCode);
		if (stockPriceDetailsList == null) {
			throw new InvalidStockException("Company Code does not exists..!");
		}

		List<StockPriceDetailsDTO> stockPriceDetailsDTOList = StockMarketUtility
				.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
		List<StockPriceDetailsDTO> stockPriceList = stockPriceDetailsDTOList.stream()
				.filter(stockPrice -> stockPrice.getStockPriceDate().isAfter(startDate)
						|| stockPrice.getStockPriceDate().isBefore(endDate))
				.collect(Collectors.toList());
		Double maxStockPrice = stockPriceList.stream().max(Comparator.naturalOrder()).get();
		
		return maxStockPrice;
	}

	public Double getAvgStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		List<StockPriceDetails> stockPriceDetailsList = stockRepository.findStockByCompanyCode(companyCode);
		if (stockPriceDetailsList == null) {
			throw new InvalidStockException("Company Code does not exists..!");
		}

		List<StockPriceDetailsDTO> stockPriceDetailsDTOList = StockMarketUtility
				.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
		List<StockPriceDetailsDTO> stockPriceList = stockPriceDetailsDTOList.stream()
				.filter(stockPrice -> stockPrice.getStockPriceDate().isAfter(startDate)
						|| stockPrice.getStockPriceDate().isBefore(endDate))
				.collect(Collectors.toList());
		Double minStockPrice = stockPriceList.stream().min(Comparator.naturalOrder()).get();
		
		return minStockPrice;
	}

	public Double getMinStockPrice(Long companyCode, LocalDate startDate, LocalDate endDate) {
		List<StockPriceDetails> stockPriceDetailsList = stockRepository.findStockByCompanyCode(companyCode);
		if (stockPriceDetailsList == null) {
			throw new InvalidStockException("Company Code does not exists..!");
		}

		List<StockPriceDetailsDTO> stockPriceDetailsDTOList = StockMarketUtility
				.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
		List<StockPriceDetailsDTO> stockPriceList = stockPriceDetailsDTOList.stream()
				.filter(stockPrice -> stockPrice.getStockPriceDate().isAfter(startDate)
						|| stockPrice.getStockPriceDate().isBefore(endDate))
				.collect(Collectors.toList());
		Double minStockPrice = stockPriceList.stream().min(Comparator.naturalOrder()).get();
		
		return minStockPrice;
	}

	public StockPriceIndexDTO getStockPriceIndex(Long companyCode, LocalDate startDate, LocalDate endDate) {

		List<StockPriceDetails> stockPriceDetailsList = stockRepository.findStockByCompanyCode(companyCode);
		if (stockPriceDetailsList == null) {
			throw new InvalidStockException("Company Code does not exists..!");
		}
		StockPriceIndexDTO stockPriceIndexDTO = new StockPriceIndexDTO();

		List<StockPriceDetailsDTO> stockPriceDetailsDTOList = StockMarketUtility
				.convertToStockPriceDetailsDtoList(stockPriceDetailsList);
		List<StockPriceDetailsDTO> stockPriceList = stockPriceDetailsDTOList.stream()
				.filter(stockPrice -> stockPrice.getStockPriceDate().isAfter(startDate)
						|| stockPrice.getStockPriceDate().isBefore(endDate))
				.collect(Collectors.toList());
		stockPriceIndexDTO.setStockPriceList(stockPriceList);
		CompanyDetails companyDetails = repository.getOne(companyCode);
		stockPriceIndexDTO.setCompanyDto(StockMarketUtility.convertToCompanyDetailsDTO(companyDetails));

		return stockPriceIndexDTO;
	}
}