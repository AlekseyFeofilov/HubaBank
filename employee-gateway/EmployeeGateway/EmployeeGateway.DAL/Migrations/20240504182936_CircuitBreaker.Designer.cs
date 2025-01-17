﻿// <auto-generated />
using System;
using EmployeeGateway.DAL;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace EmployeeGateway.DAL.Migrations
{
    [DbContext(typeof(AppDbContext))]
    [Migration("20240504182936_CircuitBreaker")]
    partial class CircuitBreaker
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.16")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            NpgsqlModelBuilderExtensions.UseIdentityByDefaultColumns(modelBuilder);

            modelBuilder.Entity("EmployeeGateway.DAL.Entity.CircuitBreakerEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<int>("CircuitBreakerStatus")
                        .HasColumnType("integer");

                    b.Property<int>("ErrorCount")
                        .HasColumnType("integer");

                    b.Property<int>("MicroserviceName")
                        .HasColumnType("integer");

                    b.Property<DateTime?>("OpenTime")
                        .HasColumnType("timestamp with time zone");

                    b.Property<int>("RequestCount")
                        .HasColumnType("integer");

                    b.HasKey("Id");

                    b.ToTable("CircuitBreakers");
                });

            modelBuilder.Entity("EmployeeGateway.DAL.Entity.ThemeEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<int>("ThemeSystem")
                        .HasColumnType("integer");

                    b.Property<Guid>("UserId")
                        .HasColumnType("uuid");

                    b.HasKey("Id");

                    b.ToTable("Themes");
                });

            modelBuilder.Entity("EmployeeGateway.DAL.Entity.UserEntity", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<string>("MessagingToken")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("Id");

                    b.ToTable("Users");
                });
#pragma warning restore 612, 618
        }
    }
}
