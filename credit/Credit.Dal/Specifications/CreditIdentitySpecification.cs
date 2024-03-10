namespace Credit.Dal.Specifications;

public class CreditIdentitySpecification : IdentitySpecification<Models.Credit, Guid> //todo убрать отображение удалённых кредитов
{
    public CreditIdentitySpecification(Guid key) : base(key)
    {
    }

    public CreditIdentitySpecification(IEnumerable<Guid>? keys) : base(keys)
    {
    }
}