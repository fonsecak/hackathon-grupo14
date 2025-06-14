/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function (knex) {
    return knex.schema.createTable('eventos', function (table) {
        table.increments('id').primary();
        table.string('nome').notNullable();
        table.string('descricao');
        table.datetime('data_hora_inicio').notNullable();
        table.datetime('data_hora_fim').notNullable();
        table.string('local').notNullable();
        table.decimal('valor_inscricao', 10, 2).defaultTo(0);
        table.string('publico_alvo');
        table.text('objetivo');
        table.string('banner');        
        table.integer('vagas_maxima');
        table.integer('id_palestrantes').unsigned().notNullable()
            .references('id').inTable('palestrantes').onDelete('CASCADE');
        table.timestamp('created_at').defaultTo(knex.fn.now());
        table.timestamp('updated_at').defaultTo(knex.fn.now());
    });
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function (knex) {
    return knex.schema.dropTable('eventos');
};
