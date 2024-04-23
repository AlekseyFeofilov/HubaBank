using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Credit.Dal.Migrations
{
    public partial class EnrichCreditAndAddCreditTerms : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<long>(
                name: "AccountsPayable",
                schema: "credit_db",
                table: "Credits",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.AddColumn<long>(
                name: "Arrears",
                schema: "credit_db",
                table: "Credits",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.AddColumn<long>(
                name: "ArrearsInterest",
                schema: "credit_db",
                table: "Credits",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.AddColumn<int>(
                name: "CollectionDay",
                schema: "credit_db",
                table: "Credits",
                type: "integer",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateOnly>(
                name: "CompletionDate",
                schema: "credit_db",
                table: "Credits",
                type: "date",
                nullable: false,
                defaultValue: new DateOnly(1, 1, 1));

            migrationBuilder.AddColumn<Guid>(
                name: "CreditTermsId",
                schema: "credit_db",
                table: "Credits",
                type: "uuid",
                nullable: true);

            migrationBuilder.AddColumn<long>(
                name: "Fine",
                schema: "credit_db",
                table: "Credits",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.AddColumn<float>(
                name: "InterestRate",
                schema: "credit_db",
                table: "Credits",
                type: "real",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<bool>(
                name: "IsDeleted",
                schema: "credit_db",
                table: "Credits",
                type: "boolean",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<long>(
                name: "Principal",
                schema: "credit_db",
                table: "Credits",
                type: "bigint",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.CreateTable(
                name: "CreditTerms",
                schema: "credit_db",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uuid", nullable: false),
                    InterestRate = table.Column<float>(type: "real", nullable: false),
                    IsDeleted = table.Column<bool>(type: "boolean", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CreditTerms", x => x.Id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Credits_CreditTermsId",
                schema: "credit_db",
                table: "Credits",
                column: "CreditTermsId");

            migrationBuilder.AddForeignKey(
                name: "FK_Credits_CreditTerms_CreditTermsId",
                schema: "credit_db",
                table: "Credits",
                column: "CreditTermsId",
                principalSchema: "credit_db",
                principalTable: "CreditTerms",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Credits_CreditTerms_CreditTermsId",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropTable(
                name: "CreditTerms",
                schema: "credit_db");

            migrationBuilder.DropIndex(
                name: "IX_Credits_CreditTermsId",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "AccountsPayable",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "Arrears",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "ArrearsInterest",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "CollectionDay",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "CompletionDate",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "CreditTermsId",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "Fine",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "InterestRate",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "IsDeleted",
                schema: "credit_db",
                table: "Credits");

            migrationBuilder.DropColumn(
                name: "Principal",
                schema: "credit_db",
                table: "Credits");
        }
    }
}
