﻿// <auto-generated />
using System;
using Credit.Dal;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace Credit.Dal.Migrations
{
    [DbContext(typeof(CreditContext))]
    [Migration("20240426195645_RenameToConfirmationKeyHash")]
    partial class RenameToConfirmationKeyHash
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasDefaultSchema("credit")
                .HasAnnotation("ProductVersion", "8.0.3")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            NpgsqlModelBuilderExtensions.UseIdentityByDefaultColumns(modelBuilder);

            modelBuilder.Entity("Credit.Dal.Models.Credit", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<Guid>("AccountId")
                        .HasColumnType("uuid");

                    b.Property<long>("Arrears")
                        .HasColumnType("bigint");

                    b.Property<long>("ArrearsInterest")
                        .HasColumnType("bigint");

                    b.Property<Guid>("BillId")
                        .HasColumnType("uuid");

                    b.Property<int>("CollectionDay")
                        .HasColumnType("integer");

                    b.Property<DateOnly>("CompletionDate")
                        .HasColumnType("date");

                    b.Property<Guid?>("CreditTermsId")
                        .HasColumnType("uuid");

                    b.Property<long>("CurrentAccountsPayable")
                        .HasColumnType("bigint");

                    b.Property<long>("Fine")
                        .HasColumnType("bigint");

                    b.Property<float>("InterestRate")
                        .HasColumnType("real");

                    b.Property<bool>("IsDeleted")
                        .HasColumnType("boolean");

                    b.Property<DateOnly>("LastArrearsUpdate")
                        .HasColumnType("date");

                    b.Property<long>("Principal")
                        .HasColumnType("bigint");

                    b.HasKey("Id");

                    b.HasIndex("CreditTermsId");

                    b.ToTable("Credits", "credit");
                });

            modelBuilder.Entity("Credit.Dal.Models.CreditTerms", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<float>("InterestRate")
                        .HasColumnType("real");

                    b.Property<bool>("IsDeleted")
                        .HasColumnType("boolean");

                    b.Property<string>("Title")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("Id");

                    b.ToTable("CreditTerms", "credit");
                });

            modelBuilder.Entity("Credit.Dal.Models.IdempotentRequest", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<bool>("Completed")
                        .HasColumnType("boolean");

                    b.Property<string>("ConfirmationKeyHash")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<int?>("HttpStatusCode")
                        .HasColumnType("integer");

                    b.Property<string>("Response")
                        .HasColumnType("text");

                    b.HasKey("Id");

                    b.ToTable("IdempotentRequests", "credit");
                });

            modelBuilder.Entity("Credit.Dal.Models.Payment", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<long>("Arrears")
                        .HasColumnType("bigint");

                    b.Property<long>("ArrearsInterest")
                        .HasColumnType("bigint");

                    b.Property<Guid>("CreditId")
                        .HasColumnType("uuid");

                    b.Property<long>("Interest")
                        .HasColumnType("bigint");

                    b.Property<long>("PaymentAmount")
                        .HasColumnType("bigint");

                    b.Property<DateOnly>("PaymentDay")
                        .HasColumnType("date");

                    b.Property<int>("PaymentStatus")
                        .HasColumnType("integer");

                    b.HasKey("Id");

                    b.HasIndex("CreditId");

                    b.ToTable("Payments", "credit");
                });

            modelBuilder.Entity("Credit.Dal.Models.Setting", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<string>("SettingName")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<string>("Value")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("Id");

                    b.ToTable("Settings", "credit");
                });

            modelBuilder.Entity("Credit.Dal.Models.Credit", b =>
                {
                    b.HasOne("Credit.Dal.Models.CreditTerms", "CreditTerms")
                        .WithMany()
                        .HasForeignKey("CreditTermsId")
                        .OnDelete(DeleteBehavior.Restrict);

                    b.Navigation("CreditTerms");
                });

            modelBuilder.Entity("Credit.Dal.Models.Payment", b =>
                {
                    b.HasOne("Credit.Dal.Models.Credit", "Credit")
                        .WithMany()
                        .HasForeignKey("CreditId")
                        .OnDelete(DeleteBehavior.Restrict)
                        .IsRequired();

                    b.Navigation("Credit");
                });
#pragma warning restore 612, 618
        }
    }
}
