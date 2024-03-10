using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Credit.Dal.Migrations
{
    public partial class AddAccountId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "AccountId",
                schema: "credit_db",
                table: "Credits",
                type: "uuid",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "AccountId",
                schema: "credit_db",
                table: "Credits");
        }
    }
}
