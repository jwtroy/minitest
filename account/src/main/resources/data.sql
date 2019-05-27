/*gt_authority*/
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531d60007','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_USER','LOGIN','user');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531d60008','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_USER_READ','LOGIN','user');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531d60009','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_USER_WRITE','LOGIN','user');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531e6000a','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_USER','LOGIN','admin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531e7000b','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_USER_READ','LOGIN','admin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531e7000c','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_USER_WRITE','LOGIN','admin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531e7000d','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_ADMIN','LOGIN','admin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531e7000e','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_ADMIN_READ','LOGIN','admin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff1626f94ba01626f9531e8000f','2018-03-29 10:27:51','','2018-03-29 10:27:51','ROLE_ADMIN_WRITE','LOGIN','admin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff163edae510163edc46cf00007','2018-06-11 15:34:23','','2018-06-11 15:34:23','ROLE_DATAADMIN','LOGIN','dataAdmin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff163edae510163edc46cf00008','2018-06-11 15:34:23','','2018-06-11 15:34:23','ROLE_USER','LOGIN','dataAdmin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff163edae510163edc46cf20009','2018-06-11 15:34:23','','2018-06-11 15:34:23','ROLE_USER_READ','LOGIN','dataAdmin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff163edae510163edc46cf2000a','2018-06-11 15:34:23','','2018-06-11 15:34:23','ROLE_USER_WRITE','LOGIN','dataAdmin');
insert into `gt_authority` (`id`, `create_at`, `enabled`, `update_at`, `authority`, `type`, `username`) values('2c929ff163edae510163edc46cf2000b','2018-06-11 15:34:23','','2018-06-11 15:34:23','ROLE_USER_REGISTER_RESOURCE','LOGIN','dataAdmin');


/*gt_client*/
insert into `gt_client` (`id`, `create_at`, `enabled`, `update_at`, `access_token_validity_seconds`, `additional_info`,`authorities`, `authorized_grant_types`, `auto_approve_scopes`, `client_id`, `client_secret`,`refresh_token_validity_seconds`, `registered_redirect_uris`, `resource_ids`, `scopes`) values ('2c929ff1626fa62301626fa6abf00000','2018-03-29 10:46:56','','2018-03-29 10:46:56','43200','{}',NULL,'client_credentials,authorization_code,password,refresh_token','*','demo1','demo1','2592000',NULL,NULL,'read,write,delete');
/*gt_department*/
insert into `gt_department` (`id`, `create_at`, `enabled`, `update_at`, `code`, `description`, `name`, `region_code`, `weight`, `parent`) values('2c929f3f5f6b3c5d015f6b4587d70000','2017-10-30 11:14:00','','2018-05-03 15:37:28','gt_320000','ddd','江苏省','320000','1',NULL);
/*gt_operation*/
insert into `gt_operation` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `name`) values ('2c929ff1626f94ba01626f9530c20000','2018-03-29 10:27:51','','2018-03-29 10:27:51','read','read');
insert into `gt_operation` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `name`) values ('2c929ff1626f94ba01626f9530dd0001','2018-03-29 10:27:51','','2018-03-29 10:27:51','write','write');
/*gt_role*/
insert into `gt_role` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `name`,`max_analysis_area`) values ('2c929ff1626f94ba01626f9531270002','2018-03-29 10:27:51','','2018-03-29 10:27:51','一般用户','user','0');
insert into `gt_role` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `name`,`max_analysis_area`) values ('2c929ff1626f94ba01626f9531300003','2018-03-29 10:27:51','','2018-03-29 10:27:51','管理员','admin','0');
insert into `gt_role` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `name`,`max_analysis_area`) values ('2c98981d6320074501633effe167000e','2018-05-08 17:05:47','','2018-05-16 16:56:49','省级角色','shengji','0');
insert into `gt_role` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `name`,`max_analysis_area`) values('2c929ff163edae510163edc383b90000','2018-06-11 15:33:24','','2018-06-11 15:33:24','数据管理员','dataAdmin','0');
/*gt_role_opera_ref*/
insert into `gt_role_opera_ref` (`role_id`, `operation_id`) values ('2c929ff1626f94ba01626f9531270002','2c929ff1626f94ba01626f9530c20000');
insert into `gt_role_opera_ref` (`role_id`, `operation_id`) values ('2c929ff1626f94ba01626f9531270002','2c929ff1626f94ba01626f9530dd0001');
insert into `gt_role_opera_ref` (`role_id`, `operation_id`) values ('2c929ff1626f94ba01626f9531300003','2c929ff1626f94ba01626f9530c20000');
insert into `gt_role_opera_ref` (`role_id`, `operation_id`) values ('2c929ff1626f94ba01626f9531300003','2c929ff1626f94ba01626f9530dd0001');
/*gt_user_info*/
insert into `gt_user_info` (`id`, `address`, `avatar`, `birthday`, `description`, `email`, `mobile`, `tel`, `zip_code`) values('2c929ff1626f94ba01626f95314b0004',NULL,NULL,'2018-03-29 10:27:51',NULL,'lanxy88@gmail.com',NULL,NULL,NULL);
/*gt_user*/
insert into `gt_user` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `expired`, `locked`, `password`, `username`, `user_info_id`,`max_analysis_area`) values('2c929ff1626f94ba01626f9531710005','2018-03-29 10:27:51','','2018-03-29 10:27:51','用户','','','7133a1cddfd6254bfdc328f97e6e2554149a6a0335b17482ce1a5fb3da2435bf8e6687559cc2781f','user','2c929ff1626f94ba01626f95314b0004','0');
insert into `gt_user` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `expired`, `locked`, `password`, `username`, `user_info_id`,`max_analysis_area`) values('2c929ff1626f94ba01626f9531730006','2018-03-29 10:27:51','','2018-03-29 10:27:51','管理员','','','e99f3afe7c6ab1abbf69fb206c54fc877335e76c8ce7df2ba94b36686e4572cef9d98cc67326afb8','admin',NULL,'0');
insert into `gt_user` (`id`, `create_at`, `enabled`, `update_at`, `alias`, `expired`, `locked`, `password`, `username`, `user_info_id`,`max_analysis_area`) values('2c929ff163edae510163edc46c170001','2018-06-11 15:34:23','','2018-06-11 15:34:23','数据管理员','','','758dabe6036e32b3cd6c3d5d0881ee78af6c2ce201454912e649936a564cf17396bd33b3757b067c','dataAdmin',NULL,'0');

/*gt_user_depart_ref*/
insert into `gt_user_depart_ref` (`user_id`, `department_id`) values('2c929ff1626f94ba01626f9531710005','2c929f3f5f6b3c5d015f6b4587d70000');
insert into `gt_user_depart_ref` (`user_id`, `department_id`) values('2c929ff1626f94ba01626f9531730006','2c929f3f5f6b3c5d015f6b4587d70000');
insert into `gt_user_depart_ref` (`user_id`, `department_id`) values('2c929ff163edae510163edc46c170001','2c929f3f5f6b3c5d015f6b4587d70000');
/*gt_user_role_ref*/
insert into `gt_user_role_ref` (`user_id`, `role_id`) values ('2c929ff1626f94ba01626f9531710005','2c929ff1626f94ba01626f9531270002');
insert into `gt_user_role_ref` (`user_id`, `role_id`) values ('2c929ff1626f94ba01626f9531710005','2c98981d6320074501633effe167000e');
insert into `gt_user_role_ref` (`user_id`, `role_id`) values ('2c929ff1626f94ba01626f9531730006','2c929ff1626f94ba01626f9531270002');
insert into `gt_user_role_ref` (`user_id`, `role_id`) values ('2c929ff1626f94ba01626f9531730006','2c929ff1626f94ba01626f9531300003');
insert into `gt_user_role_ref` (`user_id`, `role_id`) values ('2c929ff1626f94ba01626f9531730006','2c98981d6320074501633effe167000e');
insert into `gt_user_role_ref` (`user_id`, `role_id`) values ('2c929ff1626f94ba01626f9531730006','2c929ff163edae510163edc383b90000');
insert into `gt_user_role_ref` (`user_id`, `role_id`) values('2c929ff163edae510163edc46c170001','2c929ff163edae510163edc383b90000');
insert into `gt_user_role_ref` (`user_id`, `role_id`) values('2c929ff163edae510163edc46c170001','2c98981d5fe27b170160075f754a0003');
