import "@typespec/http";
import "@typespec/openapi";
import "@typespec/openapi3";

using Http;
using OpenAPI;

/**
 * ZAF API
 */
@service(#{ title: "ZAF API" })
@info(#{version: "1.0.0", contact: #{name: "Petr Pytelka", email: "petr.pytelka@lightcomp.cz" } } )
namespace ZafApi;


/**
 * Type for UUID
 */
@minLength(36)
@maxLength(36)
@format("uuid")
scalar Uuid extends string;

/**
 * Unikátní korelační identifikátor, Pro účely trasování všech (REST,WS) požadavků, DB operací, 
 * logovaných událostí, chyb, apod. 
 * 
 * Doporučený formát je UUID.
 */
@minLength(1)
@maxLength(36)
scalar HeaderCorrelationId extends string;

/**
 * Identifikátor validačního požadavku
 */
scalar ValidationRequestId extends string;

/**
 * Druh validace
 */
enum ValidationType {
  NSESSS2017,
  NSESSS2024,
  AP2023,
  DAAIP2024
}


/**
 * Požadavek na validaci
 */
model ValidationRequest {

  /**
   * Příznak spuštění v dávkovém režimu
   */
  batchMode?: boolean;

  /**
   * Druh validace, pokud není nastaven, použije se režim 
   * s automatickou detekcí typu požadavku.
   */
  validationType?: ValidationType;

  /**
   * Identifikátor požadavku
   */
  requestId?: string;

  /**
   * Binární data
   */
  data: bytes;
}

/**
 * Stav požadavku
 */
enum RequestProcessState {
  PENDING,
  PROCESSING,
  FINISHED,
  ERROR
}

/**
 * Stav validačního požadavku
 */
model ValidationRequestStatus {
  state: RequestProcessState;
  progress?: integer;
}

model ValidationStatusResponse {
  @statusCode statusCode: 200;

  @header(#{name: "X-Correlation-Id"}) xCorrelationId?: HeaderCorrelationId;

  @body
  requestStatus: ValidationRequestStatus;
}

/**
 * Odpověď na přijetí validačního požadavku
 */
model ReceivedValidationRequest {
  @statusCode statusCode: 200;

  @header(#{name: "X-Correlation-Id"}) xCorrelationId?: HeaderCorrelationId;

  /**
   * Identifikátor validačního požadavku
   */
  validationRequestId: ValidationRequestId;
}

model ValidationXmlResult {
  @statusCode statusCode: 200;

  @header contentType: "application/xml";

  data: bytes;
}

/**
 * Související entita
 */
model RelatedEntity {
  /**
   * Typ entity
   */
  type: string;

  /**
   * Identifikátor entity
   */
  id?: string;

  /**
   * Popis entity
   */
  source?: string;
}

/**
 * Pravidlo validace
 */
model ValidationRule {

  /**
   * Jednoznačný identifikátor pravidla
   */
  code: string;

  /**
   * Znění pravidla
   */
  rule: string;

  /**
   * Zdroj pravidla
   */
  source?: string;

  /**
   * Kód chyby
   */
  errorCode?: string;

  /**
   * Popis chyby
   */
  description?: string;

  /**
   * Detailní popis chyby
   */
  detail?: string;

  /**
   * Místo chyby v XML dokumentu
   */
  location?: string;

  /**
   * Související entity
   */
  relatedEntities?: RelatedEntity[];
}

/**
 * Výsledek validace úrovně kontrol
 */
enum ValidationLevelStatus {
  OK,
  ERROR,
  NOT_VALIDATED,
}

/**
 * Úroveň validace
 */
model ValidationLevel {
  /**
   * Název typu kontroly
   */
  name: string;

  /**
   * Výsledek kontroly
   */
  status: ValidationLevelStatus;

  /**
   * Název souboru jehož se kontrola týká
   */
  filaName?: string;

  /**
   * Pravidla validace
   */
  rules: ValidationRule[];
}

/**
 * Výsledek validace jednotlivého balíčku/souboru
 */
model DataPackageValidationResult {

  /**
   * Název balíčku/souboru
   */
  name?: string;

  /**
   * Identifikátor balíčku/souboru
   */
  id?: string;

  /**
   * Úrovně validace
   */
  levels: ValidationLevel[];
}

/** 
 * Výsledek validace v JSON formátu
 */
model ValidationJsonResult {
  @statusCode statusCode: 200;

  @header contentType: "application/json";

  /**
   * Identifikátor validačního požadavku
   */
  requestId?: string;

  /**
   * Název aplikace, která validaci provedla
   */
  appName: string;

  /**
   * Verze aplikace, která validaci provedla
   */
  appVersion: string;
  
  /**
   * Čas provedení validace
   */
  validationDate: offsetDateTime;

  /**
   * Druh validace
   */
  validationType: ValidationType;

  /**
   * Verze pravidel, která byla použita pro validaci
   */
  ruleVersion: int32;

  /**
   * Název profilu, který byl použit pro validaci
   */
  profile?: string;

  /**
   * Jednotlivé balíčky/soubory, které byly validovány
   */
  dataPackages: DataPackageValidationResult[];

}

@error
model Error {
  message: string;

  /**
   * Unikátní korelační identifikátor, který protéká celou atomickou byznys operací (procesem) Pro účely trasování všech (REST,WS) požadavků, DB operací, logovaných událostí, chyb, apod. Doporučený formát je UUID.
   */
  @header(#{name: "X-Correlation-Id"}) xCorrelationId: HeaderCorrelationId;
}

/**
 * User is not authorized
 */
model Unauthotized extends Error {
  @statusCode statusCode: 401;
}

/**
 * Operation was fobidden
 */
model Forbidden extends Error {
  @statusCode statusCode: 403;
}

/**
 * Request wsas not found
 */
model NotFound extends Error {
  @statusCode statusCode: 404;
}

@route("/documents")
@tag("Validation")
interface Validations {

  /** 
   * Vložení validačního požadavku
   * 
   */
  @route("validate")
  @post validate(@body body: ValidationRequest, @header(#{name: "X-Correlation-Id"}) xCorrelationId?: HeaderCorrelationId)
  : ReceivedValidationRequest | 
    Unauthotized | Forbidden;

  /**
   * Zjištění stavu validačního požadavku
   */
  @route("status/{validationRequestId}")
  @get status(@path validationRequestId: ValidationRequestId, @header(#{name: "X-Correlation-Id"}) xCorrelationId?: HeaderCorrelationId)
    :ValidationStatusResponse | NotFound;

  /**
   * Získání výsledku validačního požadavku
   */
  @route("result/{validationRequestId}")
  @get result(@path validationRequestId: ValidationRequestId, @header(#{name: "X-Correlation-Id"}) xCorrelationId?: HeaderCorrelationId)
    : ValidationXmlResult | NotFound | Unauthotized | Forbidden;
}
