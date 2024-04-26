//----------------------
// <auto-generated>
//     Generated using the NSwag toolchain v13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0)) (http://NSwag.org)
// </auto-generated>
//----------------------

using Utils.ClientGenerator.Base;

#pragma warning disable 108 // Disable "CS0108 '{derivedDto}.ToJson()' hides inherited member '{dtoBase}.ToJson()'. Use the new keyword if hiding was intended."
#pragma warning disable 114 // Disable "CS0114 '{derivedDto}.RaisePropertyChanged(String)' hides inherited member 'dtoBase.RaisePropertyChanged(String)'. To make the current member override that implementation, add the override keyword. Otherwise add the new keyword."
#pragma warning disable 472 // Disable "CS0472 The result of the expression is always 'false' since a value of type 'Int32' is never equal to 'null' of type 'Int32?'
#pragma warning disable 1573 // Disable "CS1573 Parameter '...' has no matching param tag in the XML comment for ...
#pragma warning disable 1591 // Disable "CS1591 Missing XML comment for publicly visible type or member ..."
#pragma warning disable 8073 // Disable "CS8073 The result of the expression is always 'false' since a value of type 'T' is never equal to 'null' of type 'T?'"
#pragma warning disable 3016 // Disable "CS3016 Arrays as attribute arguments is not CLS-compliant"
#pragma warning disable 8603 // Disable "CS8603 Possible null reference return"

namespace Core.Provider.v1
{
    using System = global::System;

    [System.CodeDom.Compiler.GeneratedCode("NSwag", "13.15.10.0 (NJsonSchema v10.6.10.0 (Newtonsoft.Json v13.0.0.0))")]
    public partial interface ICoreProviderV1 : IClientBase
    {
        /// <summary>
        /// Посмотреть историю транзакций по счету у пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetTransactionsV1Async(System.Guid userId, System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть историю транзакций по счету у пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetTransactionsV1Async(System.Guid userId, System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Выполнить транзакцию по счету у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CreateTransactionV1Async(System.Guid userId, System.Guid billId, TransactionCreationDto body);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Выполнить транзакцию по счету у пользователя
        /// </summary>
        /// <returns>OK</returns>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CreateTransactionV1Async(System.Guid userId, System.Guid billId, TransactionCreationDto body, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть все счета пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetUserBillsV1Async(System.Guid userId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть все счета пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetUserBillsV1Async(System.Guid userId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Создать счет для пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CreateBillV1Async(System.Guid userId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Создать счет для пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CreateBillV1Async(System.Guid userId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Выполнить транзакцию по счету у пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CreateTransactionAsync(System.Guid billId, TransactionCreationDto body);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Выполнить транзакцию по счету у пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CreateTransactionAsync(System.Guid billId, TransactionCreationDto body, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть информацию о счете пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetUserBillV1Async(System.Guid userId, System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть информацию о счете пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetUserBillV1Async(System.Guid userId, System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Закрыть счет у пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CloseUserBillV1Async(System.Guid userId, System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Закрыть счет у пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task CloseUserBillV1Async(System.Guid userId, System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть информацию о счете пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetBillAsync(System.Guid billId);

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть информацию о счете пользователя
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetBillAsync(System.Guid billId, System.Threading.CancellationToken cancellationToken);

        /// <summary>
        /// Посмотреть все счета
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetBillsV1Async();

        /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
        /// <summary>
        /// Посмотреть все счета
        /// </summary>
        /// <exception cref="ApiException">A server side error occurred.</exception>
        System.Threading.Tasks.Task GetBillsV1Async(System.Threading.CancellationToken cancellationToken);

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
    public partial class TransactionCreationDto
    {
        /// <summary>
        /// Изменение баланса счета в копейках
        /// </summary>
        [Newtonsoft.Json.JsonProperty("balanceChange", Required = Newtonsoft.Json.Required.DisallowNull, NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore)]
        public long BalanceChange { get; set; }

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


}

#pragma warning restore 1591
#pragma warning restore 1573
#pragma warning restore  472
#pragma warning restore  114
#pragma warning restore  108
#pragma warning restore 3016
#pragma warning restore 8603