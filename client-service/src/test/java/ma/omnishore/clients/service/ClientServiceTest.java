package ma.omnishore.clients.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

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

import ma.omnishore.clients.ClientServiceApplication;
import ma.omnishore.clients.domain.Client;
import ma.omnishore.clients.repository.ClientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ClientServiceApplication.class })
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientServiceTest {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private IClientService clientService;

	private Long randomId;

	@Before
	public void createClients() {

		Client randomClient = new Client("Test1", "Test1", "test1@test.ma", "Address 1");
		randomId = clientRepository.save(randomClient).getId();
	}

	@After
	public void deleteClients() {
		clientRepository.deleteAll();
	}

	@Test
	public void verifyGetAllClients() {
		List<Client> clients = clientService.getAllClients();
		assertThat(clients, hasSize(1));
	}

	@Test
	public void testGetClient() throws Exception {
		Client per = clientService.getClient(randomId);
		assertNotNull(per.getId());
	}

	@Test
	public void testCreateClient() throws Exception {
		Client randomClient2 = new Client("Test2", "Test2", "test2@test.ma", "Address 2");
		Client client = clientService.createClient(randomClient2);
		assertNotNull(client.getId());
	}

	@Test
	public void testUpdateClient() throws Exception {
		Client randomClient3 = new Client("Test2", "Test3", "test2@test.ma", "Address 2");
		randomClient3.setId(randomId);
		Client client = clientService.updateClient(randomClient3);
		assertEquals(client.getFirstName(), randomClient3.getFirstName());
	}

	@Test
	public void testDeleteClient() throws Exception {
		clientService.deleteClient(randomId);
	}

}
