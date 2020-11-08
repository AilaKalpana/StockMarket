package com.iiht.StockMarket.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.StockMarket.dto.CompanyDetailsDTO;
import com.iiht.StockMarket.exception.InvalidCompanyException;
import com.iiht.StockMarket.model.CompanyDetails;
import com.iiht.StockMarket.repository.CompanyInfoRepository;
import com.iiht.StockMarket.utils.StockMarketUtility;

@Service
@Transactional
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoRepository repository;

    public CompanyDetailsDTO saveCompanyDetails(CompanyDetailsDTO companyDetailsDTO) {

        if (companyDetailsDTO == null) {
            throw new InvalidCompanyException("company Code not found");
        }
        CompanyDetails companyDetails = StockMarketUtility.convertToCompanyDetails(companyDetailsDTO);
        repository.save(companyDetails);
        return companyDetailsDTO;
    }

    // ----------------------------------------------------------------------------
    public CompanyDetailsDTO deleteCompany(Long companyCode) {
        CompanyDetails companyDetails = repository.findCompanyDetailsById(companyCode);
        if (companyDetails == null) {
            throw new InvalidCompanyException("company Code not found");
        }
        repository.deleteByCompanyCode(companyCode);
        return new CompanyDetailsDTO();
    }

    // ----------------------------------------------------------------------------
    public CompanyDetailsDTO getCompanyInfoById(Long companyCode) {
        CompanyDetails companyDetails = repository.findCompanyDetailsById(companyCode);
        if (companyDetails == null) {
            throw new InvalidCompanyException("company Code not found");
        }
        return StockMarketUtility.convertToCompanyDetailsDTO(companyDetails);
    }

    // ----------------------------------------------------------------------------
    public List<CompanyDetailsDTO> getAllCompanies() {
        List<CompanyDetails> companyDetailsList = repository.findAll();
        return StockMarketUtility.convertToCompanyDetailsDtoList(companyDetailsList);
    }
}