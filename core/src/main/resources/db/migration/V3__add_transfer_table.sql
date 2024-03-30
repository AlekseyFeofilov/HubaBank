create table if not exists transfer (
    id uuid DEFAULT gen_random_uuid(),
    source_bill_id uuid not null,
    target_bill_id uuid not null,
    amount bigint not null,
    instant timestamptz not null DEFAULT now(),
    primary key (id),
    foreign key (source_bill_id) references bill(id),
    foreign key (target_bill_id) references bill(id),
    check (amount <> 0)
)