namespace BFF_client.Api.Database.Entities
{
    public class BillEntity
    {
        public Guid UserId { get; set; }

        public Guid BillId { get; set; }

        public bool IsHidden { get; set; }
    }
}
