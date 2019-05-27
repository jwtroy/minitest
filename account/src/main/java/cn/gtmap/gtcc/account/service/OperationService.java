package cn.gtmap.gtcc.account.service;

import cn.gtmap.gtcc.domain.sec.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * .OperationService
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 19:25
 */
public interface OperationService<O extends Operation> {

    /**
     * get operation
     *
     * @param id
     * @return
     */
    O getOperation(String id);

    /**
     * get operation by name
     *
     * @param name
     * @return
     */
    O getOperationByName(String name);

    /**
     * get Operations by pageable
     *
     * @param pageable
     * @return
     */
    Page<O> getOperations(Pageable pageable);

    /**
     * save
     *
     * @param operation
     * @return
     */
    O saveOperation(O operation);

    /**
     * save operations
     *
     * @param operations
     * @return
     */
    Iterable<O> saveOperations(Iterable<O> operations);

    /**
     * delete operation
     *
     * @param id
     */
    void deleteOperation(String id);

    /**
     * delete operation
     *
     * @param operation
     */
    void deleteOperation(O operation);

    /**
     * findAll
     * @return
     */
    public List<O> findAll();

}
