using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Credit.Dal.Migrations
{
    public partial class AddCreditTremsTitle : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Title",
                schema: "credit_db",
                table: "CreditTerms",
                type: "text",
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Title",
                schema: "credit_db",
                table: "CreditTerms");
        }
    }
}
