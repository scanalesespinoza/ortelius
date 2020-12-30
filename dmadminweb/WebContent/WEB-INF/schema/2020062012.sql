create or replace function dm.dm_insert_repo_version() returns void as $$ begin IF NOT EXISTS (SELECT 1 from dm.dm_repositoryprops a, dm.dm_repository b where a.repositoryid = b.id and a.name = 'version' and b.name = 'File System'  and b.domainid = 1) THEN insert into dm.dm_repositoryprops (repositoryid, name, value, encrypted, overridable, appendable) (select id,'version' as name, '', 'N', 'Y', 'N' from dm.dm_repository where name = 'File System' and domainid = 1);END IF;end;$$ LANGUAGE plpgsql;
