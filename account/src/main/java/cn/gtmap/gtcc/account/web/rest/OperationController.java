package cn.gtmap.gtcc.account.web.rest;

import cn.gtmap.gtcc.account.service.OperationService;
import cn.gtmap.gtcc.domain.sec.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * .OperationController
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/27 15:31
 */
@RestController
@RequestMapping("/rest/operation")
public class OperationController {
    @Autowired
    OperationService operationService;

    @GetMapping("/pageList")
    Page<Operation> getOperations(Pageable pageable) {
        return operationService.getOperations(pageable);
    }

    @GetMapping("/listAll")
    List<Operation> getOperationList() {
        return operationService.findAll();
    }

    @PutMapping("/add")
    boolean addOperation(@RequestBody Operation operation) {
        operationService.saveOperation(operation);
        return true;
    }

    @DeleteMapping("/{id}/delete")
    boolean delete(@PathVariable("id") String id) {
        operationService.deleteOperation(id);
        return true;
    }

    @GetMapping("/{id}/getOperation")
    Operation getOperation(@PathVariable("id") String id) {
        return operationService.getOperation(id);
    }
}
