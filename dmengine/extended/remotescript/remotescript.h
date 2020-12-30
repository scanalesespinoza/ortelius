#ifndef __extended_remotescript_h

#define __extended_remotescript_h


#include "../../dmapi/extended.h"
#include "../../dmapi/thread.h"


///////////////////////////////////////
// RemoteScript
///////////////////////////////////////

class RemoteScriptThread : public /*virtual*/ DMThread
{
private:
	class RemoteScriptStmtImpl &m_parent;
	class Server &m_target;
	class TransferProviderImpl *m_xfer;
	class ScopeStack *m_stack;
	char *m_shell;
	char *m_script;
	class StringList *m_params;
	bool m_usetty;

public:
	RemoteScriptThread(
		DMThreadList &threadList, class RemoteScriptStmtImpl &parent,
		class ExtendedStmt &stmt, class Server &target, class TransferProviderImpl *xfer,
		class Context &ctx, class ScopeStack *stack);
	~RemoteScriptThread();

	int execute(class Context &ctx);
};


class RemoteScriptStmtImpl : public /*virtual*/ AuditableExtendedStmtImpl, public /*virtual*/ IObject
{
private:
	class ExtendedStmt &m_parent;

	void recordRemoteScript(class Server &target, const char *cmd, int exitCode);
	void executePre(class Context &ctx);
	void executePost(int exitCode, class Context &ctx);
	bool m_usetty;

public:
	RemoteScriptStmtImpl(class DM &dm, class ExtendedStmt &parent);

	void executeWithAudit(class Context &ctx);
	void executeWithAudit(class DMThreadList &threads, class Context &ctx);
	void setUseTTY(bool usetty);

	// IObject
	OBJECT_KIND kind()  { return OBJ_KIND_STATEMENT_IMPL; }
	class Expr *getAttribute(const char *name, class Context &ctx);
	//class DMArray *getArrayAttribute(const char *name, class Context &ctx);
	//Object *getObjectAttribute(const char *name, class Context &ctx);
	class Object *toObject() { return NULL; }

	friend class RemoteScriptThread;
};


class RemoteScriptStmtImplFactory : public /*virtual*/ ExtendedStmtImplFactory
{
public:
	RemoteScriptStmtImplFactory();

	bool allowsBody()    { return false; }
	bool allowsPrePost() { return true; }
	bool isThreaded()    { return true; }
	ExtendedStmtImpl *create(class DM &dm, class ExtendedStmt &parent);
};


#endif /*__extended_remotescript_h*/
