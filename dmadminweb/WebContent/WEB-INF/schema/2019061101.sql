alter table dm.dm_componentitem add kind character varying(10);
alter table dm.dm_componentitem add buildid character varying(1024);
alter table dm.dm_componentitem add buildurl character varying(1024);
alter table dm.dm_componentitem add chart character varying(100);
alter table dm.dm_componentitem add operator character varying(100);
alter table dm.dm_componentitem add builddate character varying(100);
alter table dm.dm_componentitem add dockersha character varying(1024);
alter table dm.dm_componentitem add gitcommit character varying(1024);
alter table dm.dm_componentitem add gitrepo character varying(1024);
alter table dm.dm_componentitem add gittag character varying(100);
alter table dm.dm_componentitem add giturl character varying(1024);
update dm.dm_componentitem set kind = 'database' where rollup = 1 or rollback = 1;