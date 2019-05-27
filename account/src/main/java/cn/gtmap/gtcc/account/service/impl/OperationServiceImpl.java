package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.dao.OperationRepo;
import cn.gtmap.gtcc.account.dao.RoleRepo;
import cn.gtmap.gtcc.account.service.OperationService;
import cn.gtmap.gtcc.domain.sec.Operation;
import cn.gtmap.gtcc.ex.OperationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * .OperationServiceImpl
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 19:27
 */
@Service
public class OperationServiceImpl<O extends Operation> implements OperationService<O> {

    @Autowired
    OperationRepo<O> operationRepo;

    @Autowired
    RoleRepo roleRepo;

    /**
     * get operation
     *
     * @param id
     * @return
     */
    @Override
    public O getOperation(String id) {
        return operationRepo.findOne(id);
    }

    /**
     * get operation by name
     *
     * @param name
     * @return
     */
    @Override
    public O getOperationByName(String name) {
        Optional<O> operation = operationRepo.findByName(name);
        if (operation.isPresent()) {
            return operation.get();
        }
        throw new OperationNotFoundException(name);
    }

    /**
     * get Operations by pageable
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<O> getOperations(Pageable pageable) {
        return operationRepo.findAll(pageable);
    }

    /**
     * save
     *
     * @param operation
     * @return
     */
    @Override
    public O saveOperation(O operation) {
        return operationRepo.save(operation);
    }

    /**
     * save operations
     *
     * @param operations
     * @return
     */
    @Override
    public Iterable<O> saveOperations(Iterable<O> operations) {
        return operationRepo.save(operations);
    }

    /**
     * delete operation
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperation(String id) {
        roleRepo.deleteOperaRefByOpera(id);
        operationRepo.delete(id);
    }

    /**
     * delete operation
     *
     * @param operation
     */
    @Override
    public void deleteOperation(O operation) {
        operationRepo.delete(operation);
    }

    /**
     * findAll
     *
     * @return
     */
    @Override
    public List<O> findAll() {
        return (List<O>) operationRepo.findAll();
    }
}
