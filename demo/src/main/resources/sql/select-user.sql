SELECT
   user.id as "user.id",
   user.first_name as "user.first_name",
   user.last_name as "user.last_name",
   user.email as "user.email",
   user.phone as "user.phone",
   user.primary_address_id as "user.primary_address_id",
   user.secondary_address_id as "user.secondary_address_id",

   primary_address.id as "primary_address.id",
   primary_address.street as "primary_address.street",
   primary_address.city as "primary_address.city",
   primary_address.state as "primary_address.state",
   primary_address.zip_code as "primary_address.zip_code",
   primary_address.is_apartment as "primary_address.is_apartment",

   secondary_address.id as "secondary_address.id",
   secondary_address.street as "secondary_address.street",
   secondary_address.city as "secondary_address.city",
   secondary_address.state as "secondary_address.state",
   secondary_address.zip_code as "secondary_address.zip_code",
   secondary_address.is_apartment as "secondary_address.is_apartment"

FROM "user" user
JOIN "address" primary_address
   ON user.primary_address_id = primary_address.id
LEFT JOIN "address" secondary_address
   ON user.secondary_address_id = secondary_address.id;
