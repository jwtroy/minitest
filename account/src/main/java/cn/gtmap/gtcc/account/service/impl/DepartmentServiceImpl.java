package cn.gtmap.gtcc.account.service.impl;

import cn.gtmap.gtcc.account.dao.DepartmentRepo;
import cn.gtmap.gtcc.account.service.DepartmentService;
import cn.gtmap.gtcc.domain.sec.Department;
import cn.gtmap.gtcc.ex.DepartmentNotFounException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * .DepartmentServiceImpl
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/9/22 20:22
 */
@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl<D extends Department> implements DepartmentService<D> {

    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    DepartmentRepo<D> departmentRepo;

    /**
     * get department
     *
     * @param id
     * @return
     */
    @Override
    public D getDepartment(String id) {
        return departmentRepo.findOne(id);
    }

    /**
     * get depart by name
     *
     * @param name
     * @return
     */
    @Override
    public D getDepartmentByName(String name) {
        Optional<D> d = departmentRepo.findByName(name);
        if (d.isPresent()) return d.get();
        throw new DepartmentNotFounException(name);
    }

    /**
     * get root department
     *
     * @return
     */
    @Override
    public Iterable<D> getRootDepartments() {
        return departmentRepo.findByParentIsNull();
    }

    /**
     * get department's children
     *
     * @param parentId
     * @return
     */
    @Override
    public Iterable<D> getChildren(String parentId) {
        return (Iterable<D>) departmentRepo.findOne(parentId).getChildren();
    }

    /**
     * get deprts by pageable
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<D> getDepartments(Pageable pageable) {
        return departmentRepo.findAll(pageable);
    }

    /**
     * save
     *
     * @param department
     * @return
     */
    @Override
    @Transactional
    public D saveDepartment(D department) {
        return departmentRepo.save(department);
    }

    /**
     * save department
     *
     * @param department
     * @param parentId
     * @return
     */
    @Override
    @Transactional
    public D saveDepartment(D department, String parentId) {
        if (parentId == null) {
            return saveDepartment(department);
        }
        return saveDepartment((D) department.setParent(getDepartment(parentId)));
    }

    @Override
    @Transactional
    public void deleteDepartmentRef(String id) {
        departmentRepo.deleteDepartmentRef(id);
    }

    /**
     * save
     *
     * @param departments
     * @return
     */
    @Override
    @Transactional
    public Iterable<D> saveDepartment(Iterable<D> departments) {
        return departmentRepo.save(departments);
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteDepartment(String id) {
        departmentRepo.delete(id);
    }

    /**
     * delete
     *
     * @param department
     */
    @Override
    @Transactional
    public void deleteDepartment(D department) {
        departmentRepo.delete(department);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDepartmentById(String id) {
        try {
            departmentRepo.deleteDepartmentUser(id);
            departmentRepo.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteDepartmentUser(String departmentId, String userId) {
        try {
            departmentRepo.deleteDepartmentRef(departmentId, userId);
        } catch (Exception e) {
            logger.error(e.toString());
            return false;
        }
        return true;
    }

    @Override
    public Department findByDepartmentName(String name) {
        return departmentRepo.findByDepartmentName(name);
    }
}
