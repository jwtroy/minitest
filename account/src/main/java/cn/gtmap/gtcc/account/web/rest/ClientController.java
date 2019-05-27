package cn.gtmap.gtcc.account.web.rest;

import cn.gtmap.gtcc.account.service.ClientService;
import cn.gtmap.gtcc.domain.sec.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * .ClientController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/10/14 16:48
 */
@RestController
@RequestMapping("/rest/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/list")
    public Page<Client> getClients(Pageable pageable) {
        return clientService.getClients(pageable);
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable(name = "id") String id) {
        return clientService.getClient(id);
    }

    @PutMapping("/add")
    public Client addClient(@RequestBody Client client){
        return clientService.saveClient(client);
    }

    @DeleteMapping("/delete")
    public boolean deleteClient(@RequestParam String id){
        clientService.deleteClient(id);
        return true;
    }
}
