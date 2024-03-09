INSERT INTO configuration (device_id, device_identifier, configuration_json_string, creation_date, modification_date, zone_offset)
VALUES
    (1, 'Device001', '{"key":"value_one"}', NOW(), NOW(), '+01:00'),
    (2, 'Device002', '{"key":"value_two"}', NOW(), NOW(), '+01:00');
