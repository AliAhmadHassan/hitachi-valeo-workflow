# hitachi-valeo-workflow

This was a Java EE proof-of-concept for a structured discount approval workflow for Hitachi Valeo Service. The system combines JSF-based facelets, PrimeFaces dialogs, and a JDBC-powered DAO layer to give finance and sales teams real-time visibility over pricing requests and to enforce multi-tier approval hierarchies.

## What the application delivers
- **Order approval pipeline** driven by `PedidosBean`: a PrimeFaces data table lets approvers inspect the volume, price, margin, and reason for each discount request, then approve or deny it via a modal dialog (`WebContent/pedidos.xhtml`). Approval decisions cascade through the defined hierarchy (`PedidoAprovacao`, `HierarquiaItem`, `HierarquiaCabecalho`), locking a `Pedido` once the final reviewer signs off.
- **Visual hierarchy builder**: administrators can bootstrap approval chains and subordinates with a Google OrgChart view powered by `HierarquiaBean` (`WebContent/hierarquia.xhtml`) plus the helper JS in `static/js/workflow.orgchart.js`. The same data is exposed in the UI for adding/removing subordinates and storing the tree in `workflow_hierarquiaitem`.
- **Admin cockpit** for managing groups, users, and hierarchy templates: `cadastroGrupos.xhtml`, `cadastroUsuarios.xhtml`, and `cadastroHierarquiaCabecalhos.xhtml` each use PrimeFaces dialogs backed by `Cadastro*Bean` controllers to keep the supporting data in sync. Only admin groups (flagged in `Grupo.adm`) can reach these views thanks to `ControleDeAcesso` and the checks sprinkled through the beans.

## Architecture highlights
- **Presentation**: JSF 2.2 facelets with PrimeFaces 5.2, BootsFaces 0.6.6, and custom CSS (`static/css/master.css`, `login.css`) create responsive layouts, growl messages, and modal flows. Templates (`templates/master.xhtml`) provide the shell shared by each page.
- **Backend**: session-scoped managed beans extend `AbstractManagedBean` to read/write the current `Usuario`/`Grupo`, trigger redirects, and encapsulate login/out routines (`LoginBean`, `AbstractManagedBean.logoff`). `ControleDeAcesso` whitelists static assets and reroutes anonymous requests to `app_default.xhtml`.
- **Persistence**: a DAO layer (interfaces under `model/dao` and MySQL implementations under `model/dao/mysql/impl`) manages entities such as `Pedido`, `PedidoAprovacao`, `HierarquiaItem`, and `Grupo`. Each DAO inherits connection info from `BaseDAO` and issues PreparedStatements for the CRUD operations that feed the UI.
- **Data model**: `model` packages capture the business objects, including `Pedido` (sales parameters, margins, SAP flags) and `PedidoAprovacao` (timestamped sign-offs). `peso`, `percentages`, and approval flags all live inside the `workflow_pedido` table, while `workflow_pedidoaprovacao` tracks each step in multi-level reviews.
- **Infrastructure**: Maven builds a WAR (`pom.xml`, `maven-war-plugin`) that can be deployed on any Servlet 3.1 container configured with the Faces Servlet (`WebContent/WEB-INF/web.xml`). `persistence.xml` provides a Hibernate-backed unit (useful for future JPA refactors) but the current implementation still relies on hand-written JDBC queries for maximum control.

## Technology stack
- Java 8, Maven 3, Servlet 3.1, WAR packaging (`pom.xml`)
- JSF 2.2, PrimeFaces 5.2, BootsFaces 0.6.6 for declarative UIs
- MySQL + `mysql-connector-java` with manual `DriverManager` connections (see `BaseDAO`)
- SLF4J + Log4j (`slf4j-log4j12`) for logging and JavaMail (`javax.mail-api`, `com.sun.mail`) stubbed for future notifications
- Google Charts / OrgChart via `static/js/loader.js` for visualizing organizational structure

## Running & deployment notes
1. Fix the database connection strings in `src/br/com/valeoservice/model/dao/mysql/impl/BaseDAO.java` (currently hard-coded for the production host) or swap to environment variables before packaging.
2. `mvn clean package` produces `target/workflow.war`; deploy it to a Java EE container (Tomcat, JBoss/WildFly, etc.) configured with the Faces Servlet and a MySQL datasource matching the schema names (`workflow_pedido`, `workflow_grupo`, etc.).
3. The login page is `WebContent/app_default.xhtml`; after authentication, PrimeFaces dialogs guard CRUD operations via `@SessionScoped` beans and the `ControleDeAcesso` filter.

## What recruiters should notice
- **End-to-end design**: I designed both the approval workflow and the supporting admin utilities, weaving together UI automation (dialogs, growls), ownership checks (session beans + filters), and a JDBC DAO tier that can be swapped for JPA when the team is ready.
- **Domain modeling**: The project captures SAP-specific attributes (`codSAP`, `motivo`, `hierarquiaFinalizada`, `enviadoSAP`) inside `Pedido`, `HierarquiaCabecalho`, and `PedidoAprovacao`, so the workflow mirrors the real constraints of large-scale pricing governance.
- **UI polish**: The dashboard (`main.xhtml`) surfaces admin tasks with icon links and a responsive layout, while `pedidos.xhtml` uses PrimeFaces modals to keep approvers focused on one request at a time.

## Next steps worth exploring
1. Extract configuration for the database/email endpoints instead of hard-coding them in `BaseDAO`/`persistence.xml`.
2. Replace the manual JDBC layer with JPA/Hibernate once the schema settles, keeping the existing DAO contracts to avoid ripples in the beans.
3. Add automated integration tests (Arquillian or Selenium) to cover the login flow and the approval actions so the workflow stays stable as it evolves.
