package ma.omnishore.clients.service;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ma.omnishore.sales.SalesServiceApplication;
import ma.omnishore.sales.domain.Sale;
import ma.omnishore.sales.repository.SaleRepository;
import ma.omnishore.sales.service.ISalesService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SalesServiceApplication.class })
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SaleServiceTest {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private ISalesService saleService;

	private Long randomId;

	@Before
	public void createSales() {

		Sale randomSale = new Sale("Code1", 1L, new Date(), 3L, 10.0D);
		randomId = saleRepository.save(randomSale).getId();
	}

	@After
	public void deleteSales() {
		saleRepository.deleteAll();
	}

	@Test
	public void verifyGetAllSales() {
		List<Sale> sales = saleService.getAllSales();
		assertThat(sales, hasSize(1));
	}

	@Test
	public void testGetSale() throws Exception {
		Sale sale = saleService.getSale(randomId);
		assertNotNull(sale.getId());
	}

	@Test
	public void testGetSaleByClient() throws Exception {
		List<Sale> sales = saleService.getSalesByClient(1L);
		assertThat(sales, hasSize(1));
	}

	@Test
	public void testCreateSale() throws Exception {
		Sale randomSale2 = new Sale("Code1", 1L, new Date(), 3L, 10.0D);
		Sale sale = saleService.createSale(randomSale2);
		assertNotNull(sale.getId());
	}

	@Test
	public void testDeleteSale() throws Exception {
		saleService.deleteSale(randomId);
	}

}
