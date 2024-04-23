//----------------------
// <auto-generated>
//     Generated using the NSwag toolchain v13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0)) (http://NSwag.org)
// </auto-generated>
//----------------------

#pragma warning disable 108 // Disable "CS0108 '{derivedDto}.ToJson()' hides inherited member '{dtoBase}.ToJson()'. Use the new keyword if hiding was intended."
#pragma warning disable 114 // Disable "CS0114 '{derivedDto}.RaisePropertyChanged(String)' hides inherited member 'dtoBase.RaisePropertyChanged(String)'. To make the current member override that implementation, add the override keyword. Otherwise add the new keyword."
#pragma warning disable 472 // Disable "CS0472 The result of the expression is always 'false' since a value of type 'Int32' is never equal to 'null' of type 'Int32?'
#pragma warning disable 1573 // Disable "CS1573 Parameter '...' has no matching param tag in the XML comment for ...
#pragma warning disable 1591 // Disable "CS1591 Missing XML comment for publicly visible type or member ..."
#pragma warning disable 8073 // Disable "CS8073 The result of the expression is always 'false' since a value of type 'T' is never equal to 'null' of type 'T?'"
#pragma warning disable 3016 // Disable "CS3016 Arrays as attribute arguments is not CLS-compliant"
#pragma warning disable 8603 // Disable "CS8603 Possible null reference return"

namespace Core.Provider.v2
{
    using System = global::System;

    [System.CodeDom.Compiler.GeneratedCode("NSwag", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial interface ICoreProviderV2 : Core.Provider.Base.IClientBase
    {
        /// <summary>
        /// Посмотреть все счета пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<ClientBillDtoV2>> GetUserBillsV2Async(System.Guid userId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть все счета пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<ClientBillDtoV2>> GetUserBillsV2Async(System.Guid userId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Создать счет для пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<ClientBillDtoV2> CreateBillV2Async(System.Guid userId, BillCreationDto body);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Создать счет для пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<ClientBillDtoV2> CreateBillV2Async(System.Guid userId, BillCreationDto body, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть информацию о счете пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task<ClientBillDtoV2> GetUserBillV2Async(System.Guid userId, System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть информацию о счете пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task<ClientBillDtoV2> GetUserBillV2Async(System.Guid userId, System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Закрыть счет у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task CloseUserBillV2Async(System.Guid userId, System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Закрыть счет у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task CloseUserBillV2Async(System.Guid userId, System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть историю транзакций по счету у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<TransactionDto>> GetUserTransactionsV2Async(System.Guid userId, System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть историю транзакций по счету у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<TransactionDto>> GetUserTransactionsV2Async(System.Guid userId, System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть все счета
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<BillDtoV2>> GetBillsV2Async();

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть все счета
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<BillDtoV2>> GetBillsV2Async(System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть информацию о счете
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<BillDtoV2> GetBillV2Async(System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть информацию о счете
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<BillDtoV2> GetBillV2Async(System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Закрыть счет
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CloseBillV2Async(System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Закрыть счет
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CloseBillV2Async(System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть историю переводов по счету
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<TransferDto>> GetTransfersV2Async(System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть историю переводов по счету
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<TransferDto>> GetTransfersV2Async(System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть историю транзакций по счету у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<TransactionDto>> GetTransactionsV2Async(System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть историю транзакций по счету у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        [System.Obsolete]
        System.Threading.Tasks.Task<System.Collections.Generic.IReadOnlyList<TransactionDto>> GetTransactionsV2Async(System.Guid billId, System.Threading.CancellationToken cancellationToken);

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial class ErrorDto
    {
        /// <summary>
        /// Код ошибки. Рекомендуется использовать тип ошибки вместо кода.
        /// </summary>
        [Newtonsoft.Json.JsonProperty("code", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public int Code { get; set; }

        /// <summary>
        /// Тип ошибки
        /// </summary>
        [Newtonsoft.Json.JsonProperty("type", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public ErrorDtoType Type { get; set; }

        /// <summary>
        /// Сообщение об ошибке
        /// </summary>
        [Newtonsoft.Json.JsonProperty("message", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public string Message { get; set; }

        private System.Collections.Generic.IDictionary<string, object> _additionalProperties = new System.Collections.Generic.Dictionary<string, object>();

        [Newtonsoft.Json.JsonExtensionData]
        public System.Collections.Generic.IDictionary<string, object> AdditionalProperties
        {
            get { return _additionalProperties; }
            set { _additionalProperties = value; }
        }

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial class BillCreationDto
    {
        /// <summary>
        /// Тип валюты счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("currency", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public BillCreationDtoCurrency Currency { get; set; }

        private System.Collections.Generic.IDictionary<string, object> _additionalProperties = new System.Collections.Generic.Dictionary<string, object>();

        [Newtonsoft.Json.JsonExtensionData]
        public System.Collections.Generic.IDictionary<string, object> AdditionalProperties
        {
            get { return _additionalProperties; }
            set { _additionalProperties = value; }
        }

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial class ClientBillDtoV2
    {
        /// <summary>
        /// Идентификатор счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("id", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid Id { get; set; }

        /// <summary>
        /// Баланс счета в копейках
        /// </summary>
        [Newtonsoft.Json.JsonProperty("balance", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public long Balance { get; set; }

        /// <summary>
        /// Тип валюты счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("currency", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public ClientBillDtoV2Currency Currency { get; set; }

        private System.Collections.Generic.IDictionary<string, object> _additionalProperties = new System.Collections.Generic.Dictionary<string, object>();

        [Newtonsoft.Json.JsonExtensionData]
        public System.Collections.Generic.IDictionary<string, object> AdditionalProperties
        {
            get { return _additionalProperties; }
            set { _additionalProperties = value; }
        }

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial class TransactionDto
    {
        /// <summary>
        /// Идентификатор транзакции
        /// </summary>
        [Newtonsoft.Json.JsonProperty("id", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid Id { get; set; }

        /// <summary>
        /// Идентификатор счета транзакции
        /// </summary>
        [Newtonsoft.Json.JsonProperty("billId", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid BillId { get; set; }

        /// <summary>
        /// Изменение баланса счета в копейках
        /// </summary>
        [Newtonsoft.Json.JsonProperty("balanceChange", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public long BalanceChange { get; set; }

        /// <summary>
        /// Причина выполнения транзакции
        /// </summary>
        [Newtonsoft.Json.JsonProperty("reason", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public TransactionDtoReason Reason { get; set; }

        /// <summary>
        /// Момент времени выполнения транзакции
        /// </summary>
        [Newtonsoft.Json.JsonProperty("instant", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.DateTime Instant { get; set; }

        private System.Collections.Generic.IDictionary<string, object> _additionalProperties = new System.Collections.Generic.Dictionary<string, object>();

        [Newtonsoft.Json.JsonExtensionData]
        public System.Collections.Generic.IDictionary<string, object> AdditionalProperties
        {
            get { return _additionalProperties; }
            set { _additionalProperties = value; }
        }

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial class BillDtoV2
    {
        /// <summary>
        /// Идентификатор счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("id", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid Id { get; set; }

        /// <summary>
        /// Идентификатор клиента счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("userId", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid UserId { get; set; }

        /// <summary>
        /// Баланс счета в копейках
        /// </summary>
        [Newtonsoft.Json.JsonProperty("balance", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public long Balance { get; set; }

        /// <summary>
        /// Тип счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("type", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public BillDtoV2Type Type { get; set; }

        /// <summary>
        /// Тип валюты счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("currency", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public BillDtoV2Currency Currency { get; set; }

        /// <summary>
        /// Флаг, указывающий на то, что счет закрыт
        /// </summary>
        [Newtonsoft.Json.JsonProperty("closed", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public bool Closed { get; set; }

        private System.Collections.Generic.IDictionary<string, object> _additionalProperties = new System.Collections.Generic.Dictionary<string, object>();

        [Newtonsoft.Json.JsonExtensionData]
        public System.Collections.Generic.IDictionary<string, object> AdditionalProperties
        {
            get { return _additionalProperties; }
            set { _additionalProperties = value; }
        }

    }

    /// <summary>
    /// Счет, на который поступили деньги
    /// </summary>
    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial class BillInfoDto
    {
        /// <summary>
        /// Идентификатор счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("billId", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid BillId { get; set; }

        /// <summary>
        /// Идентификатор пользователя
        /// </summary>
        [Newtonsoft.Json.JsonProperty("userId", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid UserId { get; set; }

        /// <summary>
        /// Тип сущности
        /// </summary>
        [Newtonsoft.Json.JsonProperty("type", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public BillInfoDtoType Type { get; set; }

        /// <summary>
        /// Тип валюты счета
        /// </summary>
        [Newtonsoft.Json.JsonProperty("currency", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        [Newtonsoft.Json.JsonConverter(typeof(Newtonsoft.Json.Converters.StringEnumConverter))]
        public BillInfoDtoCurrency Currency { get; set; }

        private System.Collections.Generic.IDictionary<string, object> _additionalProperties = new System.Collections.Generic.Dictionary<string, object>();

        [Newtonsoft.Json.JsonExtensionData]
        public System.Collections.Generic.IDictionary<string, object> AdditionalProperties
        {
            get { return _additionalProperties; }
            set { _additionalProperties = value; }
        }

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial class TransferDto
    {
        /// <summary>
        /// Идентификатор перевода
        /// </summary>
        [Newtonsoft.Json.JsonProperty("id", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.Guid Id { get; set; }

        [Newtonsoft.Json.JsonProperty("source", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public BillInfoDto Source { get; set; }

        [Newtonsoft.Json.JsonProperty("target", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public BillInfoDto Target { get; set; }

        /// <summary>
        /// Изменение баланса счета в копейках
        /// </summary>
        [Newtonsoft.Json.JsonProperty("amount", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public long Amount { get; set; }

        /// <summary>
        /// Момент времени выполнения транзакции
        /// </summary>
        [Newtonsoft.Json.JsonProperty("instant", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public System.DateTime Instant { get; set; }

        private System.Collections.Generic.IDictionary<string, object> _additionalProperties = new System.Collections.Generic.Dictionary<string, object>();

        [Newtonsoft.Json.JsonExtensionData]
        public System.Collections.Generic.IDictionary<string, object> AdditionalProperties
        {
            get { return _additionalProperties; }
            set { _additionalProperties = value; }
        }

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum ErrorDtoType
    {

        [System.Runtime.Serialization.EnumMember(Value = @"UNKNOWN")]
        UNKNOWN = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"BAD_GATEWAY")]
        BAD_GATEWAY = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"CANNOT_NEGATIVE_BILL_BALANCE")]
        CANNOT_NEGATIVE_BILL_BALANCE = 2,

        [System.Runtime.Serialization.EnumMember(Value = @"CLOSING_BILL_WITH_POSITIVE_BALANCE")]
        CLOSING_BILL_WITH_POSITIVE_BALANCE = 3,

        [System.Runtime.Serialization.EnumMember(Value = @"CLOSING_BILL_WITH_NEGATIVE_BALANCE")]
        CLOSING_BILL_WITH_NEGATIVE_BALANCE = 4,

        [System.Runtime.Serialization.EnumMember(Value = @"TRANSACTION_WITH_ZERO_BALANCE_CHANGE")]
        TRANSACTION_WITH_ZERO_BALANCE_CHANGE = 5,

        [System.Runtime.Serialization.EnumMember(Value = @"CLOSING_SYSTEM_BILL")]
        CLOSING_SYSTEM_BILL = 6,

        [System.Runtime.Serialization.EnumMember(Value = @"BILL_NOT_FOUND")]
        BILL_NOT_FOUND = 7,

        [System.Runtime.Serialization.EnumMember(Value = @"CLOSED_BILL_OPERATION")]
        CLOSED_BILL_OPERATION = 8,

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum BillCreationDtoCurrency
    {

        [System.Runtime.Serialization.EnumMember(Value = @"RUB")]
        RUB = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"USD")]
        USD = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"EUR")]
        EUR = 2,

        [System.Runtime.Serialization.EnumMember(Value = @"JPY")]
        JPY = 3,

        [System.Runtime.Serialization.EnumMember(Value = @"CNY")]
        CNY = 4,

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum ClientBillDtoV2Currency
    {

        [System.Runtime.Serialization.EnumMember(Value = @"RUB")]
        RUB = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"USD")]
        USD = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"EUR")]
        EUR = 2,

        [System.Runtime.Serialization.EnumMember(Value = @"JPY")]
        JPY = 3,

        [System.Runtime.Serialization.EnumMember(Value = @"CNY")]
        CNY = 4,

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum TransactionDtoReason
    {

        [System.Runtime.Serialization.EnumMember(Value = @"TERMINAL")]
        TERMINAL = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"LOAN")]
        LOAN = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"TRANSFER")]
        TRANSFER = 2,

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum BillDtoV2Type
    {

        [System.Runtime.Serialization.EnumMember(Value = @"USER")]
        USER = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"TERMINAL")]
        TERMINAL = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"LOAN")]
        LOAN = 2,

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum BillDtoV2Currency
    {

        [System.Runtime.Serialization.EnumMember(Value = @"RUB")]
        RUB = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"USD")]
        USD = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"EUR")]
        EUR = 2,

        [System.Runtime.Serialization.EnumMember(Value = @"JPY")]
        JPY = 3,

        [System.Runtime.Serialization.EnumMember(Value = @"CNY")]
        CNY = 4,

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum BillInfoDtoType
    {

        [System.Runtime.Serialization.EnumMember(Value = @"USER")]
        USER = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"TERMINAL")]
        TERMINAL = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"LOAN")]
        LOAN = 2,

    }

    [System.CodeDom.Compiler.GeneratedCode("NJsonSchema", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public enum BillInfoDtoCurrency
    {

        [System.Runtime.Serialization.EnumMember(Value = @"RUB")]
        RUB = 0,

        [System.Runtime.Serialization.EnumMember(Value = @"USD")]
        USD = 1,

        [System.Runtime.Serialization.EnumMember(Value = @"EUR")]
        EUR = 2,

        [System.Runtime.Serialization.EnumMember(Value = @"JPY")]
        JPY = 3,

        [System.Runtime.Serialization.EnumMember(Value = @"CNY")]
        CNY = 4,

    }


}

#pragma warning restore 1591
#pragma warning restore 1573
#pragma warning restore  472
#pragma warning restore  114
#pragma warning restore  108
#pragma warning restore 3016
#pragma warning restore 8603