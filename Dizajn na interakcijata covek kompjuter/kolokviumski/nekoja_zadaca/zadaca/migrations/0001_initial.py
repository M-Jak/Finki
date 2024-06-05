# Generated by Django 5.0.6 on 2024-06-05 19:34

import django.db.models.deletion
from django.conf import settings
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Band',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=100)),
                ('genre', models.CharField(max_length=100)),
                ('country', models.CharField(max_length=100)),
                ('year_formed', models.IntegerField()),
                ('number_of_shows', models.IntegerField()),
            ],
        ),
        migrations.CreateModel(
            name='Event',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=100)),
                ('datetime', models.DateTimeField()),
                ('poster', models.ImageField(upload_to='images/')),
                ('location', models.CharField(max_length=100)),
                ('open_air', models.BooleanField()),
                ('participants', models.CharField(blank=True, max_length=200, null=True)),
                ('creator', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='BandEvent',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('band', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='zadaca.band')),
                ('event', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='zadaca.event')),
            ],
        ),
    ]
