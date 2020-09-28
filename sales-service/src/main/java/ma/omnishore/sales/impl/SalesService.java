package ma.omnishore.sales.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.omnishore.sales.domain.Sale;
import ma.omnishore.sales.repository.SaleRepository;
import ma.omnishore.sales.service.ISalesService;

/**
 * Service Implementation for managing {@link Sale}.
 */
@Service
@Transactional
public class SalesService implements ISalesService {

	private final Logger log = LoggerFactory.getLogger(SalesService.class);

	private final SaleRepository saleRepository;

	public SalesService(SaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}

	/**
	 * Save a sale.
	 * @param sale  the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public Sale createSale(Sale sale) {
		log.debug("Request to save Sale : {}", sale);
		return saleRepository.save(sale);
	}

	/**
	 * Get all the sales.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Sale> getAllSales() {
		log.debug("Request to get all Sales");
		return saleRepository.findAll();
	}

	/**
	 * Get one sale by id.
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Sale getSale(Long id) {
		log.debug("Request to get Sale : {}", id);
		return saleRepository.findById(id).orElse(null);
	}

	/**
	 * Get one sale by client id.
	 * 
	 * @param clientId  the id of the client.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Sale> getSalesByClient(Long clientId){
		log.debug("Request to get Sales By Client ID : {}", clientId);
		return saleRepository.findByClientId(clientId);
	}
	/**
	 * Delete the sale by id.
	 * @param id the id of the entity.
	 */
	@Override
	public void deleteSale(Long id) {
		log.debug("Request to delete Sale : {}", id);
		saleRepository.deleteById(id);
	}
}
