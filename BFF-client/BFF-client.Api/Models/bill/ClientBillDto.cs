namespace BFF_client.Api.model.bill
{
    public class ClientBillDto
    {
        public string Id { get; set; }

        public long Balance { get; set; }

        public bool isHidden { get; set; }
    }
}
