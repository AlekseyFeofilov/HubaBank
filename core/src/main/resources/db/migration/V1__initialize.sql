create table if not exists bill (
    id uuid DEFAULT gen_random_uuid(),
    user_id uuid not null,
    balance decimal(19, 2) not null DEFAULT 0,
    creation_instant timestamptz not null DEFAULT now(),
    closing_instant timestamptz,
    primary key (id)
);

create table if not exists transaction (
    id uuid DEFAULT gen_random_uuid(),
    bill_id uuid not null,
    balance_change decimal(19, 2) not null,
    reason varchar not null,
    instant timestamptz not null DEFAULT now(),
    primary key (id),
    foreign key (bill_id) references bill(id),
    check (balance_change <> 0)
)